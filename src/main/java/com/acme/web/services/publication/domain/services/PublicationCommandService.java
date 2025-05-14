package com.acme.web.services.publication.domain.services;

import com.acme.web.services.publication.domain.model.commands.CreatePublicationCommand;
import com.acme.web.services.publication.domain.model.commands.DeletePublicationCommand;
import com.acme.web.services.publication.domain.model.commands.UpdatePublicationCommand;


public interface PublicationCommandService {
    Long handle(CreatePublicationCommand command);
    Long handle(UpdatePublicationCommand command);
    void handle(DeletePublicationCommand command);
}