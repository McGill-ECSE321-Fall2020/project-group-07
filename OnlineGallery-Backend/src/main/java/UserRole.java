import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Id;

@Entity
public abstract class UserRole{
   private User user;
   
   @ManyToOne(optional=false)
   public User getUser() {
      return this.user;
   }
   
   public void setUser(User user) {
      this.user = user;
   }
   
   private Integer roleId;

public void setRoleId(Integer value) {
    this.roleId = value;
}
@Id
public Integer getRoleId() {
    return this.roleId;
}
}
