package org.siri_hate.main_service.model.dto.response.article;

import java.time.LocalDate;

public record ArticleFullResponse(long id, String title, String owner, String previewUrl, String category,
                                  String content, LocalDate publicationDate, boolean moderationPassed) {

}
