package com.buyakov.ja.chatop.api.mapper;

import com.buyakov.ja.chatop.api.dto.RentalDto;
import com.buyakov.ja.chatop.api.dto.RentalResponse;
import com.buyakov.ja.chatop.api.model.Rental;
import com.buyakov.ja.chatop.api.model.User;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface RentalMapper {

    @Mapping(target = "picture", source="pictureFile")
    @Mapping(target = "owner", source="owner")
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "id", ignore = true)
    Rental createToEntity(RentalDto dto, String pictureFile, User owner);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "owner", ignore = true),
            @Mapping(target = "picture", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true)
    })
    void updateEntityFromDto(RentalDto dto, @MappingTarget Rental rental);


    @Mapping(target = "ownerId", source = "owner.id")
    @Mapping(
            target = "picture",
            expression = "java(rental.getPicture() != null ? \"http://localhost:3001/images/rentals/\" + rental.getPicture() : null)"
    )
    RentalResponse toResponse(Rental rental);

    List<RentalResponse> toResponseList(List<Rental> rentals);
}
