package gr.gt.cf.imdbapp.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovieDTO {
    private String title;
    private int year;
    private int runningTimeInMinutes;
    private String leadActor;
}
