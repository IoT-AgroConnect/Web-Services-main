package com.acme.web.services.publication.domain.model.commands;

import java.util.Date;

public record UpdatePublicationCommand(Long publicationId, String title, String description, String image) {
}
