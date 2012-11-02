
import static org.fest.assertions.Assertions.assertThat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.junit.*;

public class UpgradeCheckTest {
	
	String CURRENT_PATH = System.getProperty("user.dir") + "/test/";
	
	@After
    public void after() {
        rm_rf(new File(CURRENT_PATH));
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
	public void getRemote() {
		//Given
		
		//When
				
		//Then
		
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
		String gitPath = CURRENT_PATH + ".git";
		Repository testRepo = new RepositoryBuilder().setGitDir(new File(gitPath)).build();
		testRepo.create(false);
		Git testGit = new Git(testRepo);
		String testFilePath = CURRENT_PATH + "readme.txt";
		BufferedWriter out = new BufferedWriter(new FileWriter(testFilePath));
		ArrayList<String> tagList = new ArrayList<String>();
		
		//When
        out.write("hello 1");
        out.flush();
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
		
		//When
				
		//Then
		
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
	public void deleteTag() {
		//Given
		
		//When
				
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
