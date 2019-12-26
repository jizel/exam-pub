package cz.etyka.exam.pub.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor(staticName="of")
public class Role {

    @Id
    @GeneratedValue
    private long id;
    @NonNull private Role.RoleType roleType;

    public enum RoleType {
        BARTENDER,
        GUEST
    }
}
