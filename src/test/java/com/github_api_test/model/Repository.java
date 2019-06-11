
package com.github_api_test.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Repository {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("private")
    @Expose
    private Boolean _private;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("homepage")
    @Expose
    private String homepage;

    @SerializedName("has_issues")
    @Expose
    private Boolean hasIssues;
    @SerializedName("has_projects")
    @Expose
    private Boolean hasProjects;
    @SerializedName("has_wiki")
    @Expose
    private Boolean hasWiki;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPrivate() {
        return _private;
    }

    public void setPrivate(Boolean _private) {
        this._private = _private;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Boolean getHasIssues() {
        return hasIssues;
    }

    public void setHasIssues(Boolean hasIssues) {
        this.hasIssues = hasIssues;
    }

    public Boolean getHasProjects() {
        return hasProjects;
    }

    public void setHasProjects(Boolean hasProjects) {
        this.hasProjects = hasProjects;
    }

    public Boolean getHasWiki() {
        return hasWiki;
    }

    public void setHasWiki(Boolean hasWiki) {
        this.hasWiki = hasWiki;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (this.getClass() != obj.getClass()) return false;
        // Class name is Employ & have lastname
        Repository repository = (Repository) obj;
        return this.name.equals(repository.getName()) && this._private.equals(repository.getPrivate()) &&
                this.homepage.equals(repository.getHomepage()) && this.description.equals(repository.getDescription()) &&
                this.hasIssues.equals(repository.getHasIssues()) && this.hasProjects.equals(repository.getHasProjects()) &&
                this.hasWiki.equals(repository.getHasWiki());
    }
}
