package com.juja.parsing;

import com.sun.jndi.toolkit.url.Uri;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParsingJsoupTest {
    private static final String URL = "http://4pda.ru";
    public static void main(String[] args) throws IOException {
        List<Article> articleList = new ArrayList<>();
        Document doc = Jsoup.connect(URL).get();

        Elements h1Elements = doc.getElementsByAttributeValue("class", "list-post-title");

        h1Elements.forEach(h1Element -> {
            Element aElement = h1Element.child(0);
            String url = aElement.attr("href");
            String title = aElement.child(0).text();

            articleList.add(new Article(url, title));
        });

        articleList.forEach(System.out::println);
    }
}

class Article {
    private String url;
    private String name;

    public Article(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Article{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
