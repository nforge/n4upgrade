
import static org.fest.assertions.Assertions.assertThat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.lib.StoredConfig;
import org.junit.*;

public class UpgradeCheckTest {
	
	final String TEST_PATH = "resources/test/repo/git/";
	
	@After
    public void after() {
        rm_rf(new File(TEST_PATH));
    }
	
	@Test
	public void UpgrateCheck() throws Exception{
		//When
		UpgradeCheck n4 = new UpgradeCheck();
		
		//Then
		assertThat(n4.getLocal()).isNotNull();
		assertThat(n4.getLocalGit()).isNotNull();
		assertThat(n4.getRemoteURI()).isNotNull();
	}
	
	@Test
	public void getRemote() throws IOException {
		//Given
		String gitPath = TEST_PATH + ".git";
		Repository testRepo = new RepositoryBuilder().setGitDir(new File(gitPath)).build();
		testRepo.create(false);
		StoredConfig config = testRepo.getConfig();
		String fakeUrl = "fakeurl.git";
		String remoteName = "branch";
		
		//When
		config.setString("remote", remoteName, "url", fakeUrl);
		config.save();
		String remoteUrl = UpgradeCheck.getRemote(testRepo);
		
		//Then
		assertThat(remoteUrl).isEqualTo(fakeUrl);
	}
	
	@Test
	public void hasNoTags() {
		//Given
		
		//When
				
		//Then
		
	}
	
	@Test
	public void fetchGit() {
		//Given
		
		//When
				
		//Then
		
	}
	
	@Test
	public void getTags() throws Exception {
		//Given
		String gitPath = TEST_PATH + ".git";
		Repository testRepo = new RepositoryBuilder().setGitDir(new File(gitPath)).build();
		testRepo.create(false);
		Git testGit = new Git(testRepo);
		String testFilePath = TEST_PATH + "readme.txt";
		BufferedWriter out = new BufferedWriter(new FileWriter(testFilePath));
		ArrayList<String> tagList = new ArrayList<String>();
		
		//When
        out.write("hello 1");
        out.flush();
        out.close();
        testGit.add().addFilepattern("readme.txt").call();
        testGit.commit().setMessage("commit 1").call();
		
		tagList.add("1.0");
		tagList.add("2.0");
		for(String tag : tagList){
			testGit.tag().setName(tag).call();
		}
		ArrayList<String> tags = UpgradeCheck.getTags(testRepo);
		
		//Then
		assertThat(tags.size()).isEqualTo(2);
		assertThat(tags.get(0)).isEqualTo("1.0");
		assertThat(tags.get(1)).isEqualTo("2.0");
	}
	
	@Test
	public void findLastTag() {
		//Given
		ArrayList<String> tags = new ArrayList<String>();
		
		//When
		tags.add("0.2");
		tags.add("1.0");
		tags.add("1.4");
		tags.add("3.9");
		tags.add("11.0");
		String tag = UpgradeCheck.findLastTag(tags);
		
		//Then
		assertThat(tag).isEqualTo("11.0");
	}
	
	@Test
	public void sortTags(){
		//Given
		ArrayList<String> tags = new ArrayList<String>();
		
		//when
		tags.add("8.8");
		tags.add("2.0");
		tags.add("11.3");
		tags.add("1.5");
		tags = UpgradeCheck.sortTags(tags);
		
		//Then
		assertThat(tags.get(0)).isEqualTo("11.3");
		assertThat(tags.get(1)).isEqualTo("8.8");
		assertThat(tags.get(3)).isEqualTo("1.5");
	}
	
	@Test
	public void isLatestVersion() throws Exception {
		//Given
		
		//When
				
		//Then
		
	}
	
	@Test
	public void getUpdatedTags() {
		//Given
		
		//When
				
		//Then
		
	}
	
	@Test
	public void merge() {
		//Given
		
		//When
				
		//Then
		
	}
	
	@Test
	public void deleteTags() {
		//Given
		
		//When
				
		//Then
		
	}
	
	@Test
	public void deleteTag() throws IOException, NoFilepatternException, GitAPIException {
		//Given
		String gitPath = TEST_PATH + ".git";
		Repository testRepo = new RepositoryBuilder().setGitDir(new File(gitPath)).build();
		testRepo.create(false);
		Git testGit = new Git(testRepo);
		String testFilePath = TEST_PATH + "readme.txt";
		BufferedWriter out = new BufferedWriter(new FileWriter(testFilePath));
		ArrayList<String> tagList = new ArrayList<String>();
		
		//When
		out.write("hello 1");
		out.flush();
	    out.close();
	    testGit.add().addFilepattern("readme.txt").call();
	    testGit.commit().setMessage("commit 1").call();
		
		tagList.add("1.0");
		tagList.add("2.0");
		for(String tag : tagList){
			testGit.tag().setName(tag).call();
		}
		
		//Then
		
	}
	
	private void rm_rf(File file) {
        assert file != null;
        if (file.isDirectory()) {
            File[] list = file.listFiles();
            assert list != null;
            for(int i = 0; i < list.length; i++){
                rm_rf(list[i]);
            }
        }
        file.delete();
    }
}