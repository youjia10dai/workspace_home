package sutdy.spring.beanutils.entity;



import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * @author clj
 * @date 2019-05-05
 */

public class SailUser implements Serializable {
	private static final long serialVersionUID = 3333203914045554199L;

	private String userId;
	
	private String areaName;
	

	private String companyName;
	
	private Set<SailClub> sailClubs = new HashSet<SailClub>();
	
	public Set<SailClub> getSailClubs() {
		return sailClubs;
	}

	public void setSailClubs(Set<SailClub> sailClubs) {
		this.sailClubs = sailClubs;
	}

	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public String toString() {
		return "SailUser [userId=" + userId + ", areaName=" + areaName + ", companyName=" + companyName + ", sailClubs="
				+ sailClubs + "]";
	}
	
}
