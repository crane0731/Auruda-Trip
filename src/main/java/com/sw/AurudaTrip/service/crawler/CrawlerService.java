package com.sw.AurudaTrip.service.crawler;

import org.springframework.stereotype.Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CrawlerService {


    // 축제 정보를 가져오는 서비스
    public List<Map<String, String>> fetchLatestFestivalData(String region, int pageCount) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\dlwns\\source\\chromedriver.exe");
        //System.setProperty("webdriver.chrome.driver", "/var/jenkins_home/workspace/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<Map<String, String>> festivalList = new ArrayList<>();

        try {
            String searchUrl = "https://search.naver.com/search.naver?where=nexearch&sm=top_sly.hst&fbm=0&acr=1&ie=utf8&query=%EC%B6%95%EC%A0%9C";
            driver.get(searchUrl);

            // 페이지가 완전히 로드될 때까지 대기
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".list_type.switch")));

            // 드롭다운 영역의 aria-hidden 속성을 변경
            WebElement filterArea = driver.findElement(By.cssSelector("div.cm_filter_area._select_panel"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].setAttribute('aria-hidden', 'false')", filterArea);

            // 지역 필터 적용
            if (region != null && !region.isEmpty()) {
                // 드롭다운 토글을 찾고 열기
                WebElement dropdownToggle = driver.findElement(By.cssSelector("li.tab._select_trigger > a[onclick*='goOtherTCR']"));

                // aria-expanded 속성이 "false"인 경우 드롭다운을 열기
                String ariaExpanded = dropdownToggle.getAttribute("aria-expanded");
                if (ariaExpanded != null && ariaExpanded.equals("false")) {
                    dropdownToggle.click();
                    wait.until(ExpectedConditions.attributeToBe(dropdownToggle, "aria-expanded", "true"));
                }

                // 원하는 지역을 가진 `li` 요소를 클릭
                List<WebElement> regionOptions = driver.findElements(By.cssSelector("li[data-text]"));
                boolean regionFound = false;
                for (WebElement option : regionOptions) {
                    if (region.equals(option.getAttribute("data-text"))) {
                        option.click();  // 지역을 클릭하여 선택
                        regionFound = true;
                        Thread.sleep(1000); // 변경 후 대기 시간
                        break;
                    }
                }

                if (!regionFound) {
                    System.out.println("해당 지역을 찾을 수 없습니다: " + region);
                    return festivalList;
                }
            }


            // pageCount에 해당하는 페이지로 이동
            for (int i = 1; i < pageCount; i++) {
                try {
                    WebElement nextPageButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.pg_next:not([aria-disabled='true'])")));
                    nextPageButton.click();
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".card_area")));
                } catch (Exception e) {
                    System.out.println("해당 페이지로 이동할 수 없습니다.");
                    return festivalList;
                }
            }

            // 현재 페이지 데이터 추출
            String pageSource = driver.getPageSource();
            Document doc = Jsoup.parse(pageSource);

            Elements cardAreas = doc.select(".card_area");
            if (cardAreas.size() >= pageCount) {
                Element currentCardArea = cardAreas.get(pageCount - 1);
                Elements cardItems = currentCardArea.select(".card_item");

                for (Element cardItem : cardItems) {
                    Map<String, String> festival = new HashMap<>();

                    Element imgElement = cardItem.selectFirst(".img_box img");
                    if (imgElement != null) {
                        festival.put("image", imgElement.attr("src"));
                    }

                    Element titleElement = cardItem.selectFirst(".title .this_text a");
                    if (titleElement != null) {
                        festival.put("title", titleElement.text());
                        festival.put("link", titleElement.attr("href")); // 클릭 링크 추가
                    } else {
                        festival.put("title", "제목 없음"); // 기본값 설정 또는 해당 항목을 건너뜁니다
                    }

                    Element statusElement = cardItem.selectFirst(".this_text"); // 상태 가져오기
                    if (statusElement != null) {
                        festival.put("status", statusElement.text());
                    }

                    Element dateElement = cardItem.select(".rel_info dt:contains(기간) + dd.no_ellip").first();
                    if (dateElement != null) {
                        festival.put("date", dateElement.text());
                    }

                    Element locationElement = cardItem.select(".rel_info dt:contains(장소) + dd").first();
                    if (locationElement != null) {
                        festival.put("location", locationElement.text());
                    }

                    festivalList.add(festival);
                }
            } else {
                System.out.println("요청한 페이지에 데이터가 없습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }

        // 데이터가 없을 경우 "데이터가 없습니다" 메시지 추가
        if (festivalList.isEmpty()) {
            Map<String, String> noDataMessage = new HashMap<>();
            noDataMessage.put("message", "데이터가 없습니다.");
            festivalList.add(noDataMessage);
        }

        return festivalList;
    }

    // 콘서트 정보를 가져오는 메서드
    public List<Map<String, String>> fetchConcertData(int pageCount, String filter, String region) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\dlwns\\source\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        List<Map<String, String>> concertList = new ArrayList<>();

        try {
            String searchUrl = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=%EC%BD%98%EC%84%9C%ED%8A%B8";
            driver.get(searchUrl);

            // 페이지가 완전히 로드될 때까지 대기
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".list_image_info.type_pure._panel_wrapper")));

            // 필터 선택
            WebElement filterButton;
            if ("popular".equals(filter)) { // 인기 순 필터 선택
                filterButton = driver.findElement(By.cssSelector("li.tab[data-value='popular'] a"));
            } else { // 기본값: 날짜 순 필터
                filterButton = driver.findElement(By.cssSelector("li.tab[data-value='date'] a"));
            }
            filterButton.click();
            Thread.sleep(1000); // 필터가 적용되도록 잠시 대기

            // 지역 필터 적용
            if (region != null && !region.isEmpty()) {
                // 드롭다운 토글을 찾고 열기
                WebElement dropdownToggle = driver.findElement(By.cssSelector("li.tab._custom_select_trigger[data-key='u1']"));
                String ariaExpanded = dropdownToggle.getAttribute("aria-expanded");

                if (ariaExpanded == null || ariaExpanded.equals("false")) {
                    dropdownToggle.click();
                    wait.until(ExpectedConditions.attributeToBe(dropdownToggle, "aria-expanded", "true"));

                    // "aria-hidden"을 false로 설정하여 드롭다운이 표시되도록 함
                    WebElement filterArea = driver.findElement(By.cssSelector("div.cm_filter_area._custom_select_panel"));
                    ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('aria-hidden', 'false')", filterArea);
                }

                // 원하는 지역을 가진 `li` 요소를 클릭
                List<WebElement> regionOptions = driver.findElements(By.cssSelector("li.item._item[data-text]"));
                boolean regionFound = false;
                for (WebElement option : regionOptions) {
                    if (region.equals(option.getAttribute("data-text"))) {
                        option.click();  // 지역을 클릭하여 선택
                        regionFound = true;
                        Thread.sleep(1000); // 변경 후 대기 시간
                        break;
                    }
                }

                if (!regionFound) {
                    System.out.println("해당 지역을 찾을 수 없습니다: " + region);
                    return concertList;
                }
            }

            // 페이지 이동 루프
            for (int i = 1; i < pageCount; i++) {
                try {
                    WebElement nextPageButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.pg_next:not([aria-disabled='true'])")));
                    nextPageButton.click();
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".list_image_info.type_pure._panel_wrapper")));
                    Thread.sleep(1000); // 페이지 로딩 시간을 위해 잠시 대기
                } catch (Exception e) {
                    System.out.println("페이지가 더 이상 존재하지 않습니다.");
                    driver.quit();
                    return concertList;
                }
            }

            // 현재 페이지의 데이터를 추출
            String pageSource = driver.getPageSource();
            Document doc = Jsoup.parse(pageSource);

            Elements cardAreas = doc.select(".list_image_info.type_pure._panel_wrapper ul");
            if (cardAreas.size() >= pageCount) {
                Element currentCardArea = cardAreas.get(pageCount - 1);
                Elements cardItems = currentCardArea.select("li");

                for (Element cardItem : cardItems) {
                    Map<String, String> concert = new HashMap<>();

                    // 이미지 URL
                    Element imgElement = cardItem.selectFirst(".thumb img");
                    if (imgElement != null) {
                        concert.put("image", imgElement.attr("src"));
                    }

                    // 콘서트 제목
                    Element titleElement = cardItem.selectFirst(".title_box .name");
                    if (titleElement != null) {
                        concert.put("title", titleElement.text());
                    }

                    // 콘서트 장소
                    Element locationElement = cardItem.selectFirst(".sub_text.line_1");
                    if (locationElement != null) {
                        concert.put("location", locationElement.text());
                    }

                    // 상세 링크
                    Element linkElement = cardItem.selectFirst("a");
                    if (linkElement != null) {
                        concert.put("link", linkElement.attr("href"));
                    }

                    // this_text 값 (예: "공연중"과 같은 상태 정보)
                    Element statusElement = cardItem.selectFirst(".this_text");
                    if (statusElement != null) {
                        concert.put("status", statusElement.text());
                    }

                    concertList.add(concert);
                }
            } else {
                System.out.println("요청한 페이지에 데이터가 없습니다.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }

        // 데이터가 없을 경우 "데이터가 없습니다" 메시지 추가
        if (concertList.isEmpty()) {
            Map<String, String> noDataMessage = new HashMap<>();
            noDataMessage.put("message", "데이터가 없습니다.");
            concertList.add(noDataMessage);
        }

        return concertList;
    }
}