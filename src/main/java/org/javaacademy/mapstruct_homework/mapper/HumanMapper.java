package org.javaacademy.mapstruct_homework.mapper;

import org.javaacademy.mapstruct_homework.dto.PersonCreditDto;
import org.javaacademy.mapstruct_homework.dto.PersonDriverLicenceDto;
import org.javaacademy.mapstruct_homework.dto.PersonInsuranceDto;
import org.javaacademy.mapstruct_homework.entity.Human;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;

@Mapper
public interface HumanMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passportNumber", source = ".", qualifiedByName = "getPassportNumber")
    @Mapping(target = "salary", source = ".", qualifiedByName = "getSalary")
    @Mapping(target = "fullAddress", source = ".", qualifiedByName = "getFullAddress")
    PersonCreditDto convertToCreditDto(Human human);

    @Mapping(target = "fullName", source = ".", qualifiedByName = "getFullName")
    @Mapping(target = "fullPassportData", source = ".", qualifiedByName = "getFullPassportData")
    @Mapping(target = "birthDate", source = ".", qualifiedByName = "getBirthDate")
    PersonDriverLicenceDto convertToDriverLicenceDto(Human human);

    @Mapping(target = "fullName", source = ".", qualifiedByName = "getFullName")
    @Mapping(target = "fullAddress", source = ".", qualifiedByName = "getFullAddress")
    @Mapping(target = "fullAge", source = ".", qualifiedByName = "getFullAge")
    PersonInsuranceDto convertToInsuranceDto(Human human);

    @Named("getPassportNumber")
    default String getPassportNumber(Human human) {
        return "%s%s".formatted(
                human.getPassport().getSeries(),
                human.getPassport().getNumber()
        );
    }

    @Named("getSalary")
    default String getSalary(Human human) {
        return "%s %s".formatted(
                human.getWork().getSalary(),
                human.getWork().getCurrency()
        );
    }

    @Named("getFullAddress")
    default String getFullAddress(Human human) {
        StringBuilder fullAddressBuilder = new StringBuilder();
        createString(human.getLivingAddress().getRegion(), fullAddressBuilder);
        createString(human.getLivingAddress().getCity(), fullAddressBuilder);
        createString(human.getLivingAddress().getStreet(), fullAddressBuilder);
        createString(human.getLivingAddress().getHouse(), fullAddressBuilder);
        createString(human.getLivingAddress().getBuilding(), fullAddressBuilder);
        createString(human.getLivingAddress().getFlat(), fullAddressBuilder);
        return fullAddressBuilder.toString();
    }

    @Named("getFullName")
    default String getFullName(Human human) {
        StringBuilder fullNameBuilder = new StringBuilder();
        createString(human.getFirstName(), fullNameBuilder);
        createString(human.getLastName(), fullNameBuilder);
        createString(human.getMiddleName(), fullNameBuilder);
        return fullNameBuilder.toString();
    }

    @Named("getFullPassportData")
    default String getFullPassportData(Human human) {
        LocalDate issueDate = human.getPassport().getIssueDate();

        return "%s%s %d.%d.%d".formatted(
                human.getPassport().getSeries(),
                human.getPassport().getNumber(),
                issueDate.getDayOfMonth(),
                issueDate.getMonthValue(),
                issueDate.getYear()
        );
    }

    @Named("getBirthDate")
    default String getBirthDate(Human human) {
        return "%d.%d.%d".formatted(
                human.getBirthDay(),
                human.getBirthMonth(),
                human.getBirthYear()
        );
    }

    @Named("getFullAge")
    default Integer getFullAge(Human human) {
        return LocalDate.now().getYear() - human.getBirthYear();
    }

    private void createString(String field, StringBuilder stringBuilder) {
        if (field != null && !field.isEmpty()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(field);
        }
    }
}


