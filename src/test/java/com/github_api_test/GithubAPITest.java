package com.github_api_test;

import com.github_api_test.model.Repository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.test.api.helper.TestDataReader;
import com.test.api.network.APIRequest;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GithubAPITest extends TestDataReader {

    private final String AUTHORIZATION = "Authorization";

    @Test(priority = 1)
    public void testGithubCreateRepo() {
        String uri = getPropValue("repo.url");
        String token = getPropValue("token");
        String payload = loadFile("sample_repo.json");

        APIRequest.POST(uri)
                .header(AUTHORIZATION, "token " + token)
                .body(payload)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .invoke()
                .assertStatus(201)
                .getBody();
    }

    @Test(priority = 2)
    public void testGithubStarRepo() {
        String uri = getPropValue("repo.star.url");
        String token = getPropValue("token");
        String repoName = getPropValue("repo.name");
        String email = getPropValue("username");

        APIRequest.PUT(uri)
                .header(AUTHORIZATION, "token " + token)
                .header("Content-Length", String.valueOf(0))
                .pathParam("owner", email)
                .pathParam("repo", repoName)
                .invoke()
                .assertStatus(204);
    }

    @Test(priority = 3)
    public void testGithubRepoData() {
        String uri = getPropValue("repo.url");
        String token = getPropValue("token");
        String localData = loadFile("sample_repo.json");

        String repoRespose = APIRequest.GET(uri)
                .header(AUTHORIZATION, "token " + token)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .invoke()
                .assertStatus(200)
                .getBody();

        Type listType = new TypeToken<ArrayList<Repository>>() {
        }.getType();
        Gson gson = new GsonBuilder().create();

        List<Repository> repositoryList = gson.fromJson(repoRespose, listType);
        Repository localRepository = gson.fromJson(localData, Repository.class);
        boolean matched = false;

        for (Repository repository : repositoryList) {
            if (repository.equals(localRepository)) {
                matched = true;
                break;
            }
        }

        Assert.assertTrue(matched);
    }

    @AfterTest
    public void testGithubRepoDelete() {
        String uri = getPropValue("repo.delete.url");
        String token = getPropValue("token");
        String repoName = getPropValue("repo.name");
        String email = getPropValue("username");

        APIRequest.DELETE(uri)
                .header(AUTHORIZATION, "token " + token)
                .pathParam("owner", email)
                .pathParam("repo", repoName)
                .invoke()
                .assertStatus(204);
    }
}
