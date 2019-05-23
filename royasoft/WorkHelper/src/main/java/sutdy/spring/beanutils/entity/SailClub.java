package sutdy.spring.beanutils.entity;



import java.io.Serializable;
import java.util.Set;

/**
 * @author clj
 * @date 2019-05-05
 */

public class SailClub implements Serializable{
	
	private static final long serialVersionUID = 9185459844938273153L;
	

	private Integer clubId;
	
	private String testName;

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	private String clubName;
	
	private Set<SailUser> sailUsers;
	
	public Set<SailUser> getSailUsers() {
		return sailUsers;
	}

	public void setSailUsers(Set<SailUser> sailUsers) {
		this.sailUsers = sailUsers;
	}

	public Integer getClubId() {
		return clubId;
	}

	public void setClubId(Integer clubId) {
		this.clubId = clubId;
	}

	public String getClubName() {
		return clubName;
	}

	public void setClubName(String clubName) {
		this.clubName = clubName;
	}

	@Override
	public String toString() {
		return "SailClub [clubId=" + clubId + ", testName=" + testName + ", clubName=" + clubName + ", sailUsers="
				+ sailUsers + "]";
	}

}
