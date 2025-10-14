package com.testinium.base;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.testinium.driver.TestiniumSeleniumDriver;
import com.testinium.model.ElementInfo;
import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
//import org.apache.commons.lang.StringUtils;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;


public class BaseTest {

    protected static WebDriver driver;
    protected static Actions actions;
    protected Logger logger = LoggerFactory.getLogger(getClass());
    DesiredCapabilities capabilities;
    ChromeOptions chromeOptions;
    FirefoxOptions firefoxOptions;

    String browserName = "chrome";
    String selectPlatform = "mac";

    private static final String DEFAULT_DIRECTORY_PATH = "elementValues";
    ConcurrentMap<String, Object> elementMapList = new ConcurrentHashMap<>();

    /**
     *
     */
    @BeforeScenario
    public void setUp() {
        logger.info("************************************  BeforeScenario  ************************************");
        try {
                logger.info("************************************   Testiniumda test ayağa kalkacak   ************************************");
                ChromeOptions options = new ChromeOptions();
                //capabilities = DesiredCapabilities.chrome();

                //options.setExperimentalOption("w3c", false);
                options.addArguments("disable-translate");
                options.addArguments("--disable-notifications");
                options.addArguments("--disable-web-security");
                options.addArguments("--allow-running-insecure-content");
                options.addArguments("--allow-cross-origin-auth-prompt");

                Map<String, Object> prefs = new HashMap<>();
                options.setExperimentalOption("prefs", prefs);
                DesiredCapabilities capabilities = new DesiredCapabilities();

                //  capabilities.setCapability(ChromeOptions.CAPABILITY, options);
               // capabilities.setCapability("key", System.getenv("key"));
                String keyValue = System.getenv("key");
                if (keyValue == null) {
                    keyValue = "varsayilan_deger";
                    System.err.println("Environment variable 'key' is not set. Using default value: " + keyValue);
                }
                options.setCapability("testinium:key", keyValue);
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);


                browserName = System.getenv("browser");
                //driver = new RemoteWebDriver(new URL("http://172.25.1.110:4444/wd/hub"), capabilities);
                driver = new TestiniumSeleniumDriver(new URL("http://172.25.1.110:4444/wd/hub"), capabilities);
                //driver = new RemoteWebDriver(new URL("http://hub.testinium.io/wd/hub"), capabilities);
                actions = new Actions(driver);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @AfterScenario
    public void tearDown() {
        driver.quit();
    }

    public void initMap(File[] fileList) {
        Type elementType = new TypeToken<List<ElementInfo>>() {
        }.getType();
        Gson gson = new Gson();
        List<ElementInfo> elementInfoList = null;
        for (File file : fileList) {
            try {
                elementInfoList = gson
                        .fromJson(new FileReader(file), elementType);
                elementInfoList.parallelStream()
                        .forEach(elementInfo -> elementMapList.put(elementInfo.getKey(), elementInfo));
            } catch (FileNotFoundException e) {
                logger.warn("{} not found", e);
            }
        }
    }

    public File[] getFileList() {
        File[] fileList = new File(
                this.getClass().getClassLoader().getResource(DEFAULT_DIRECTORY_PATH).getFile())
                .listFiles(pathname -> !pathname.isDirectory() && pathname.getName().endsWith(".json"));
        if (fileList == null) {
            logger.warn(
                    "File Directory Is Not Found! Please Check Directory Location. Default Directory Path = {}",
                    DEFAULT_DIRECTORY_PATH);
            throw new NullPointerException();
        }
        return fileList;
    }

    /**
     * Set Chrome options
     *
     * @return the chrome options
     */

    public ChromeOptions chromeOptions() {
        // Chrome sürücü seçeneklerini yapılandırma (isteğe bağlı)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        // İhtiyaca göre seçenekleri ekleyin
        return options;

    }

    private FirefoxOptions firefoxOptions() {
        // Firefox sürücü seçeneklerini yapılandırma (isteğe bağlı)
        FirefoxOptions options = new FirefoxOptions();
        // İhtiyaca göre seçenekleri ekleyin
        return options;
    }

    /**
     * Set Firefox options
     *
     * @return the firefox options
     */


    public ElementInfo findElementInfoByKey(String key) {
        return (ElementInfo) elementMapList.get(key);
    }

    public void saveValue(String key, String value) {
        elementMapList.put(key, value);
    }

    public String getValue(String key) {
        return elementMapList.get(key).toString();
    }

}



