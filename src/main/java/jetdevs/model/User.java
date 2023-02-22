package jetdevs.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class User {

    private int id;
    private String userName;
    private String password;
    private String role;

}
