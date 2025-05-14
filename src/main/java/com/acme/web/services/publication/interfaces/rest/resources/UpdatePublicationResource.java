package com.acme.web.services.publication.interfaces.rest.resources;

public record UpdatePublicationResource(
        String title,
        String description,
        String image
        ) {}
