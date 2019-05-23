package sutdy.spring.beanutils.vo;

import java.io.Serializable;
import java.util.Set;

public class SailUserVo implements Serializable{
	
	private static final long serialVersionUID = -159175612250383005L;

	private String userId;
	
	private String areaName;
	
	private String companyName;
	
	private Set<SailClubVo> sailClubs;

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

	public Set<SailClubVo> getSailClubs() {
		return sailClubs;
	}

	public void setSailClubs(Set<SailClubVo> sailClubs) {
		this.sailClubs = sailClubs;
	}

	@Override
	public String toString() {
		return "SailUserVo [userId=" + userId + ", areaName=" + areaName + ", companyName=" + companyName
				+ ", sailClubs=" + sailClubs + "]";
	}

}