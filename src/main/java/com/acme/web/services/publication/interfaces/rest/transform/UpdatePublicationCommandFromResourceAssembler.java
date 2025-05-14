package com.acme.web.services.publication.interfaces.rest.transform;

import com.acme.web.services.publication.domain.model.commands.UpdatePublicationCommand;
import com.acme.web.services.publication.interfaces.rest.resources.UpdatePublicationResource;

public class UpdatePublicationCommandFromResourceAssembler {
    public static UpdatePublicationCommand toCommandFromResource(UpdatePublicationResource resource, Long id) {
        return new UpdatePublicationCommand(
                id,
                resource.title(),
                resource.description(),
                resource.image()
        );
    }
}
