package qq.upyachka.js.challenge.core.model;

import com.google.common.collect.Sets;

import javax.persistence.*;
import java.util.Set;

/**
 * Represent platform user.
 * Created on 24.02.17.
 * @author upyachka.
 */
@Entity
@Table(name = "user_", uniqueConstraints = {@UniqueConstraint(columnNames = {"id", "username"})})
public class User {

    /** Unique identifier. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** User-friendly identifier. */
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    /** User password. */
    @Column(name = "password", nullable = false)
    private String password;

    /** Set of assigned to user roles. */
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = Sets.newHashSet();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ScriptExecutionResultDo> executions = Sets.newHashSet();

    /** @returns value of {@link #executions}. */
    public Set<ScriptExecutionResultDo> getExecutions() {
        return executions;
    }

    /** @param {@link #executions} value for {@link #executions}. */
    public void setExecutions(Set<ScriptExecutionResultDo> executions) {
        this.executions = executions;
    }

    /** @returns value of {@link #id}. */
    public Long getId() {
        return id;
    }

    /** @param {@link #id} value for {@link #id}. */
    public void setId(Long id) {
        this.id = id;
    }

    /** @returns value of {@link #username}. */
    public String getUsername() {
        return username;
    }

    /** @param {@link #username} value for {@link #username}. */
    public void setUsername(String username) {
        this.username = username;
    }

    /** @returns value of {@link #password}. */
    public String getPassword() {
        return password;
    }

    /** @param {@link #password} value for {@link #password}. */
    public void setPassword(String password) {
        this.password = password;
    }

    /** @returns value of {@link #roles}. */
    public Set<Role> getRoles() {
        return roles;
    }

    /** @param {@link #roles} value for {@link #roles}. */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}