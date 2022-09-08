package springboot.rickandmorty.dto.external;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiInfoDto {
    private int count;
    private int pages;
    private String next;
    private String prev;
}
