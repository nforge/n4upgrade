

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.DetachedHeadException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;

public class Upgrader {
	public void upgrade(){
		UpgradeCheck n4 = new UpgradeCheck();
		try {
			n4.setRepositories();
			if(n4.isLatetestVersion()) {
				System.out.println(" 최신 버전입니다.");
			} 
			else {
				ArrayList<String> updatedTags = n4.getUpdatedTags();
				System.out.println(" 최신 버전이 존재합니다.");
				System.out.print(" 업그레이드 하시겠습니까?(y/n)  : ");
				if(getYesNoKey()) {
					n4.merge(updatedTags);
					System.out.println(" 업그레이드 완료 되었습니다.");
				}
				else {
					n4.deleteTags(updatedTags);
					System.out.println(" 업그레이드가 취소되었습니다.");
				}
			}
				
		} catch (WrongRepositoryStateException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		} catch (DetachedHeadException e) {
			e.printStackTrace();
		} catch (InvalidRemoteException e) {
			e.printStackTrace();
		} catch (CanceledException e) {
			e.printStackTrace();
		} catch (RefNotFoundException e) {
			e.printStackTrace();
		} catch (NoHeadException e) {
			e.printStackTrace();
		} catch (TransportException e) {
			e.printStackTrace();
		} catch (GitAPIException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean getYesNoKey() {
	    int key;
	    Scanner oScanner = new java.util.Scanner(System.in);
	    do { 
	    	key = oScanner.findInLine(".").charAt(0);
	        if (key == 'y' || key == 'Y') return true;
	        if (key == 'n' || key == 'N') return false;
	    } while (key != 0);

	      return false; 
	  }
}
