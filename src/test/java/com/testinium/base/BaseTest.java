package com.testinium.base;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.testinium.driver.TestiniumSeleniumDriver;
import com.testinium.model.ElementInfo;
import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BaseTest {

    protected static WebDriver driver;
    protected static Actions actions;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private String browserName = System.getenv("BROWSER");              // "chrome" / "firefox"
    private String gridBaseUrl = "http://172.25.1.110:4444";
    private String testiniumKey = System.getenv("key");                 // testinium:key

    private static final String DEFAULT_DIRECTORY_PATH = "elementValues";
    private final ConcurrentMap<String, Object> elementMapList = new ConcurrentHashMap<>();

    @BeforeScenario
    public void setUp() {
        logger.info("************************************  BeforeScenario  ************************************");

        try {
            URL remoteUrl = new URL(normalizeGridUrl(gridBaseUrl));
            driver = createTestiniumDriver(remoteUrl, browserName, testiniumKey);
            actions = new Actions(driver);

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
            driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));

            logger.info("Driver başlatıldı: {} @ {}", browserName, remoteUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Grid URL hatalı: " + gridBaseUrl, e);
        }
    }

    private WebDriver createTestiniumDriver(URL remoteUrl, String browser, String key) {
        if ("firefox".equalsIgnoreCase(browser)) {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setCapability("testinium:key", key);
            return new TestiniumSeleniumDriver(remoteUrl, firefoxOptions);
        } else {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--disable-notifications");
            chromeOptions.addArguments("--disable-web-security");
            chromeOptions.addArguments("--allow-running-insecure-content");
            chromeOptions.addArguments("--allow-cross-origin-auth-prompt");
            chromeOptions.addArguments("--remote-allow-origins=*");

            Map<String, Object> prefs = new HashMap<String, Object>();
            chromeOptions.setExperimentalOption("prefs", prefs);

            chromeOptions.setCapability("testinium:key", key);

            return new TestiniumSeleniumDriver(remoteUrl, chromeOptions);
        }
    }

    private String normalizeGridUrl(String base) {
        String trimmed = base.endsWith("/") ? base.substring(0, base.length() - 1) : base;
        if (trimmed.endsWith("/wd/hub")) {
            return trimmed;
        }
        return trimmed + "/wd/hub";
    }

    @AfterScenario
    public void tearDown() {
        logger.info("************************************  AfterScenario (quit)  ************************************");
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                logger.warn("Driver quit sırasında hata: {}", e.getMessage());
            } finally {
                driver = null;
                actions = null;
            }
        }
    }

    // -------------------- Element JSON haritalama --------------------

    public void initMap(File[] fileList) {
        Type elementType = new TypeToken<List<ElementInfo>>() {}.getType();
        Gson gson = new Gson();

        for (File file : fileList) {
            try (FileReader fileReader = new FileReader(file)) {
                List<ElementInfo> elementInfoList = gson.fromJson(fileReader, elementType);
                if (elementInfoList != null) {
                    for (ElementInfo elementInfo : elementInfoList) {
                        elementMapList.put(elementInfo.getKey(), elementInfo);
                    }
                }
            } catch (FileNotFoundException e) {
                logger.warn("{} not found", e.getMessage());
            } catch (Exception e) {
                logger.warn("Element JSON yüklenirken hata: {}", e.getMessage());
            }
        }
    }

    public File[] getFileList() {
        java.net.URL resourceUrl = this.getClass().getClassLoader().getResource(DEFAULT_DIRECTORY_PATH);
        if (resourceUrl == null) {
            logger.warn("File Directory Is Not Found! Default Directory Path = {}", DEFAULT_DIRECTORY_PATH);
            throw new NullPointerException("elementValues klasörü bulunamadı");
        }

        File dir = new File(resourceUrl.getFile());
        File[] fileList = dir.listFiles(pathname ->
                !pathname.isDirectory() && pathname.getName().endsWith(".json"));

        if (fileList == null) {
            logger.warn("File list is null. Path: {}", dir.getAbsolutePath());
            throw new NullPointerException("JSON dosyaları okunamadı");
        }
        return fileList;
    }

    public ElementInfo findElementInfoByKey(String key) {
        Object value = elementMapList.get(key);
        if (value instanceof ElementInfo) {
            return (ElementInfo) value;
        }
        return null;
    }

    public void saveValue(String key, String value) {
        elementMapList.put(key, value);
    }

    public String getValue(String key) {
        Object value = elementMapList.get(key);
        return value == null ? null : value.toString();
    }
}
