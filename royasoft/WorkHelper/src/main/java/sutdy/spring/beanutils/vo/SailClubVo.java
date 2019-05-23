package sutdy.spring.beanutils.vo;


import java.io.Serializable;
import java.util.Set;

public class SailClubVo implements Serializable{

	private static final long serialVersionUID = 680508646352322158L;

	private Integer clubId;
	
	private String clubName;
	
	private Set<SailUserVo> sailUsers;

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

	public Set<SailUserVo> getSailUsers() {
		return sailUsers;
	}

	public void setSailUsers(Set<SailUserVo> sailUsers) {
		this.sailUsers = sailUsers;
	}

	@Override
	public String toString() {
		return "SailClubVo [clubId=" + clubId + ", clubName=" + clubName + ", sailUsers=" + sailUsers + "]";
	}

}
