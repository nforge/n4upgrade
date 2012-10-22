package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.DetachedHeadException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;

public class UpgradeCheck {

	Repository local; 
	Repository master;
	
	public void setRepositories() throws IOException{
		this.local = new RepositoryBuilder().findGitDir().build();
		this.master = new RepositoryBuilder().findGitDir(new File("https://github.com/youngje/nforge4.git")).build();
	}
	
	public ArrayList<String> getTags(Repository repository){
		Iterator<String> tagKeys = repository.getTags().keySet().iterator();
		ArrayList<String> tags = new ArrayList<String>();
		
		while(tagKeys.hasNext()){
			tags.add(tagKeys.next());
		}
		return tags;
	}

	public String findLastTag(ArrayList<String> tags){
		Collections.sort(tags);
		return tags.get(0);
	}
	
	public void isNew() throws WrongRepositoryStateException, InvalidConfigurationException, DetachedHeadException, InvalidRemoteException, CanceledException, RefNotFoundException, NoHeadException, TransportException, GitAPIException{
		String localTag = findLastTag(getTags(local));
		String masterTag = findLastTag(getTags(master));
		
		if(localTag.equals(masterTag)){
			System.out.println("최신 버전입니다.");
		}
		else{
			Git git = new Git(local);
			if(git.pull().setTimeout(500).call().isSuccessful()){
				System.out.println("upgrade가 완료 되었습니다.");
			}
			else{
				System.out.println("upgrade가 실패하였습니다.");
			}
		}
	}
	
}
