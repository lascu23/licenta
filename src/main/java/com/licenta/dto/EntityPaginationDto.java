package com.licenta.dto;

import com.licenta.entity.DoctorProfile;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class EntityPaginationDto<T> {
    private List<String> profileImages;
    private Page<T> profiles;
    private int currentPage;
    private int totalPages;

    public EntityPaginationDto(List<String> profileImages, Page<T> profiles, int currentPage, int totalPages) {
        this.profileImages = profileImages;
        this.profiles = profiles;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }

    public EntityPaginationDto(Page<T> profiles, int currentPage, int totalPages) {
        this.profileImages = null;
        this.profiles = profiles;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }

    public EntityPaginationDto(){}
}
