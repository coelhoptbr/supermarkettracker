package com.coelho.supermarkettracker.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection="userinfo")
public class UserInfo {

    @NotNull
    private ObjectId id;
    @NotNull
    private String userId;
    @NotNull
    private String fullName;
    @NotNull
    private String email;

    public UserInfo() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInfo)) return false;
        UserInfo userInfo = (UserInfo) o;
        return getId().equals(userInfo.getId()) && getUserId().equals(userInfo.getUserId()) && getFullName().equals(userInfo.getFullName()) && getEmail().equals(userInfo.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserId(), getFullName(), getEmail());
    }
}
