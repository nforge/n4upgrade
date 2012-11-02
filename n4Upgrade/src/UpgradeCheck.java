// 주석, refactoring, testunit

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.jgit.api.DeleteTagCommand;
import org.eclipse.jgit.api.FetchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeCommand;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.DetachedHeadException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.api.errors.InvalidMergeHeadsException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.TagOpt;

public class UpgradeCheck {
	
	public final static String  REMOTE_BRANCH = "refs/heads/upgrade";
	
	private Repository local;
	private Git localGit;
	private ArrayList<String> localTags;
	private ArrayList<String> remoteTags;
	private String remoteURI;
	
	public UpgradeCheck() throws InvalidRemoteException, TransportException, IOException, GitAPIException {
		setLocal();
		setLocalGit();
		setRemoteURI(this.local);
	}
	
	public Repository getLocal() {
		return this.local;
	}
	
	public void setLocal() throws IOException, InvalidRemoteException, TransportException, GitAPIException{
		this.local = new RepositoryBuilder().findGitDir().build();
	}
	
	public Git getLocalGit() {
		return this.localGit;
	}
	
	public void setLocalGit() {
		this.localGit = new Git(local);		
	}
	
	public void setRepository(String gitPath) throws IOException {
		this.local = new RepositoryBuilder().setGitDir(new File(gitPath)).build();
	}
	
	public String getRemoteURI() {
		return this.remoteURI;
	}
	
	public void setRemoteURI(Repository local) {
		StoredConfig storedConfig = local.getConfig();
		Set<String> remotes = storedConfig.getSubsections("remote");
		String remoteName = (String) remotes.toArray()[0];
		this.remoteURI = storedConfig.getString("remote", remoteName, "url");
	}
	
	public ArrayList<String> getLocalTags() {
		return this.localTags;
	}
	
	public void setLocalTags() {
		this.localTags = getTags(local);
	}
	
	public ArrayList<String> getRemoteTags() {
		return this.remoteTags;
	}
	
	public void setRemoteTags() {
		this.remoteTags = getTags(local);
	}
	
	/* 
	 * 현재 local 저장소의 원격 저장소의 주소(URL) 받아오기
	 */
	public static String getRemote(Repository local){
		Config storedConfig = local.getConfig();
		Set<String> remotes = storedConfig.getSubsections("remote");
		String remoteName = (String) remotes.toArray()[0];

		return storedConfig.getString("remote", remoteName, "url");
	}
	
	/*
	 * 현재 저장소에 Tag 정보의 유무 확인 
	 */
	public boolean hasNoTags() { 
		return getTags(local).isEmpty();
	}
	
	/* 
	 * 원격 저장소의 tag 내역을 local 저장소로 fetch.
	 */
	public void fetchGit() throws InvalidRemoteException, TransportException, GitAPIException{
		FetchCommand fetch = localGit.fetch();
		fetch.setRemote(remoteURI).setTagOpt(TagOpt.FETCH_TAGS).setRefSpecs(new RefSpec(REMOTE_BRANCH)).call();
	}
	
	/*
	 * 저장소의 Tag 목록 받아오기
	 */
	public static ArrayList<String> getTags(Repository repository){
		Iterator<String> tagKeys = repository.getTags().keySet().iterator();
		ArrayList<String> tags = new ArrayList<String>();
		
		while(tagKeys.hasNext()){
			tags.add(tagKeys.next());
		}

		return tags;
	}

	/*
	 * tag 목록 중에서 제일 최신 버전 찾아내기
	 */
	public static String findLastTag(ArrayList<String> tags){
		return sortTags(tags).get(0);
	}
	
	public static ArrayList<String> sortTags(ArrayList<String> tags) {
		Collections.sort(tags, new Comparator<String>() {
			public int compare(String tag1, String tag2) {
				return Double.valueOf(tag1)>Double.valueOf(tag2)?-1:1;
			}
		});
		return tags;
	}
	
	/*
	 * 현재 local 저장소의 버전이 원격 저장소와 비교했을 때 최신 버전인지 확인
	 * 	최신버전일 경우 true, 아닐 경우 false  return
	 */
	public boolean isLatetestVersion() throws WrongRepositoryStateException, InvalidConfigurationException, DetachedHeadException, InvalidRemoteException, CanceledException, RefNotFoundException, NoHeadException, TransportException, GitAPIException{
		setLocalTags();
		String lastLocalTag = findLastTag(localTags);
		
		fetchGit();
		
		setRemoteTags();
		String lastRemoteTag = findLastTag(remoteTags);
		
		System.out.println(" - Current version : " + lastLocalTag);
		System.out.println(" - The latest version : " + lastRemoteTag);
		
		return lastLocalTag.equals(lastRemoteTag);
	}
	
	/*
	 * 원격 저장소에서 받아온 새로운 버전의 tag 모음 반환
	 *    local에 없는 tag list를 반환한다
	 */
	public ArrayList<String> getUpdatedTags(){
		for(String tag : localTags) {
			if(remoteTags.contains(tag)) {
				remoteTags.remove(tag);
			}
		}
		return remoteTags;
	}
	
	/*
	 * 새로운 버전의 tag로 local 저장소를 merge 
	 */
	public void merge(ArrayList<String> updatedTags) throws IOException, NoHeadException, ConcurrentRefUpdateException, CheckoutConflictException, InvalidMergeHeadsException, WrongRepositoryStateException, NoMessageException, GitAPIException {
		merge(findLastTag(updatedTags));
	}
	
	/*
	 * 새로운 버전의 tag로 local 저장소를 merge 
	 */
	public void merge(String optionTag) throws IOException, NoHeadException, ConcurrentRefUpdateException, CheckoutConflictException, InvalidMergeHeadsException, WrongRepositoryStateException, NoMessageException, GitAPIException {
		Ref tag = local.getRef(optionTag);
		MergeCommand merge = localGit.merge();
		merge.include(tag).call();
	}
	
	/*
	 * upgrade를 원치 않을 경우 받아온 tag 목록 삭제
	 */
	public void deleteTags(ArrayList<String> updatedTags) throws GitAPIException {
		for(String tag : updatedTags) {
			deleteTag(tag);
		}
	}
	
	/*
	 * 특정 tag 삭제
	 */
	public void deleteTag(String tag) throws GitAPIException {
			DeleteTagCommand delete = localGit.tagDelete();
			delete.setTags(tag).call();
	}
	
}
