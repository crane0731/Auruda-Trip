package com.sw.AurudaTrip.dto.itinerary;

import com.sw.AurudaTrip.domain.Theme;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ItiernaryRequestDto {

    @NotBlank(message = "제목을 입력하세요.")
    private String title;
    @NotNull(message = "여행기간을 입력하세요.")
    private Long travelDay;
    @NotBlank(message = "여행 시작일을 입력하세요.")
    private String startDate;
    @NotBlank(message = "여행 종료일을 입력하세요.")
    private String endDate;

    @NotBlank(message = "지역을 입력하세요.")
    private String region;

    @NotNull(message = "테마를 입력하세요.")
    private Theme theme1;
    @NotNull(message = "테마를 입력하세요.")
    private Theme theme2;

    private Theme theme3;

    @Size(min = 0, message = "장소 리스트는 비어 있을 수 있습니다.")
    @Builder.Default
    private List<PlaceDto> places = new ArrayList<>();

}
