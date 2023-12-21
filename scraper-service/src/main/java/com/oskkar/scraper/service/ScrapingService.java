package com.oskkar.scraper.service;

import com.oskkar.scraper.information.ClassInformation;
import com.oskkar.scraper.information.GroupList;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ScrapingService {
    private static final String URL = "https://sia.unal.edu.co/Catalogo/facespublico/public/servicioPublico.jsf?taskflowId=task-flow-AC_CatalogoAsignaturas";
    private static final String[] SECTIONS = {"pt1:r1:0:soc1::content",  "pt1:r1:0:soc2::content", "pt1:r1:0:soc3::content"};

    private final ChromeDriver driver;
    private final WebDriverWait wait;

    @PostConstruct
    void postConstruct() {
       scrape();
    }

    public void scrape() {
        driver.get(URL);
        selectOptionByVisibleText(wait, By.id("pt1:r1:0:soc1::content"), "Pregrado");
        selectOptionByVisibleText(wait, By.id("pt1:r1:0:soc2::content"), "2055 FACULTAD DE INGENIERÍA");
        selectOptionByVisibleText(wait, By.id("pt1:r1:0:soc3::content"), "2983 INGENIERÍA ELÉCTRICA");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='pt1:r1:0:cb1' and @class='af_button p_AFTextOnly']"))).click();

        intoAClass(driver, "2016489", wait);
        obtainGroup(driver, wait);
    }
    private static void iterOverSections() {
        for (int i = 0; i < SECTIONS.length; i++) {

        }
    }
    private static void selectOptionByVisibleText(WebDriverWait wait, By locator, String visibleText) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        Select select = new Select(element);
        select.selectByVisibleText(visibleText);
    }

    private static void intoAClass(ChromeDriver driver, String idNumber, WebDriverWait wait) {
        WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("af_table_data-body")));
        List<WebElement> rows = table.findElements(By.tagName("a"));
        for (WebElement row : rows) {
            if (row.getAccessibleName().equals(idNumber)) {
                row.click();
                break;
            }
        }
    }

    public static void obtainGroup(ChromeDriver driver, WebDriverWait wait) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[text()='Información de la asignatura']")));
        String className = driver.findElement(By.tagName("h2")).getText();
        GroupList groupList = new GroupList(className, new ArrayList<>());
        List<WebElement> list = driver.findElements(By.cssSelector(".borde.salto.af_panelGroupLayout"));

        for (WebElement webElement : list) {

            if (webElement.getText().contains("Grupo")) {

                String groupNumber = webElement.findElement(By.tagName("h2")).getText().split(" ")[2];
                WebElement to = webElement.findElement(By.cssSelector(".margin-t.af_panelGroupLayout"));
                List<WebElement> list1 = to.findElements(By.tagName("div"));
                List<String> list2 = new ArrayList<>();
                list2.add(groupNumber);
                for (WebElement element : list1) {
                    if (element.getText().contains("Facultad:") || element.getText().contains("Duración:") || element.getText().contains("Jornada:")) {
                        continue;
                    }
                    list2.add(element.getText().split(": ")[1]);
                }
                String group = list2.get(0);
                String teacher = list2.get(1);
                String schedule = list2.get(2);
                String availableSpots = list2.get(3);
                groupList.addToClassInformation(new ClassInformation(group, teacher, schedule, availableSpots));

            }
        }
        System.out.println(groupList);

    }
}
