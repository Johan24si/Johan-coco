package com.example.johan_coco.Home

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

object RssParser {

    fun parse(xmlString: String): List<Post> {
        val posts = mutableListOf<Post>()

        try {
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            parser.setInput(xmlString.reader())

            var title = ""
            var pubDate = ""
            var link = ""
            var description = ""
            var thumbnail: String? = null
            var insideItem = false
            var currentTag = ""

            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        currentTag = parser.name ?: ""
                        when (currentTag) {
                            "item" -> {
                                insideItem = true
                                title = ""
                                pubDate = ""
                                link = ""
                                description = ""
                                thumbnail = null
                            }
                            "enclosure" -> {
                                if (insideItem) {
                                    thumbnail = parser.getAttributeValue(null, "url")
                                }
                            }
                            "media:content", "media:thumbnail" -> {
                                if (insideItem && thumbnail == null) {
                                    thumbnail = parser.getAttributeValue(null, "url")
                                }
                            }
                        }
                    }

                    XmlPullParser.TEXT -> {
                        if (insideItem) {
                            val text = parser.text?.trim() ?: ""
                            when (currentTag) {
                                "title" -> title += text
                                "pubDate" -> pubDate += text
                                "link" -> link += text
                                "description" -> description += text
                            }
                        }
                    }

                    XmlPullParser.END_TAG -> {
                        if (parser.name == "item" && insideItem) {
                            insideItem = false
                            if (title.isNotEmpty()) {
                                posts.add(
                                    Post(
                                        title = title.trim(),
                                        pubDate = pubDate.trim(),
                                        link = link.trim(),
                                        description = description.trim().ifEmpty { null },
                                        thumbnail = thumbnail
                                    )
                                )
                            }
                        }
                        currentTag = ""
                    }
                }
                eventType = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return posts
    }
}