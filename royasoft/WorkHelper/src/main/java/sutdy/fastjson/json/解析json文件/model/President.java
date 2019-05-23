package sutdy.fastjson.json.½âÎöjsonÎÄ¼þ.model;

import java.util.List;

public class President {
//	private String userId;
	private List<Arrayy> array;
	
    @Override
	public String toString() {
		return "President [userId="  + ", array=" + array + "]";
	}



	public List<Arrayy> getArray() {
		return array;
	}

	public void setArray(List<Arrayy> array) {
		this.array = array;
	}

	public static class Arrayy{
    	private Integer clubId;
    	private Integer[] cheakArr;
    	private Member member;


		public Member getMember() {
			return member;
		}

		public void setMember(Member member) {
			this.member = member;
		}

		public Integer getClubId() {
			return clubId;
		}

		public void setClubId(Integer clubId) {
			this.clubId = clubId;
		}

		public Integer[] getCheakArr() {
			return cheakArr;
		}

		public void setCheakArr(Integer[] cheakArr) {
			this.cheakArr = cheakArr;
		}


		public static class Member{
    		private String name1;
    		private String tel1;
    		private String name2;
    		private String tel2;
    		private String name3;
    		public String getName1() {
				return name1;
			}
			public void setName1(String name1) {
				this.name1 = name1;
			}
			public String getTel1() {
				return tel1;
			}
			public void setTel1(String tel1) {
				this.tel1 = tel1;
			}
			public String getName2() {
				return name2;
			}
			public void setName2(String name2) {
				this.name2 = name2;
			}
			public String getTel2() {
				return tel2;
			}
			public void setTel2(String tel2) {
				this.tel2 = tel2;
			}
			public String getName3() {
				return name3;
			}
			public void setName3(String name3) {
				this.name3 = name3;
			}
			public String getTel3() {
				return tel3;
			}
			public void setTel3(String tel3) {
				this.tel3 = tel3;
			}
			public String getName4() {
				return name4;
			}
			public void setName4(String name4) {
				this.name4 = name4;
			}
			public String getTel4() {
				return tel4;
			}
			public void setTel4(String tel4) {
				this.tel4 = tel4;
			}
			private String tel3;
    		private String name4;
    		private String tel4;
    	}
	}
	
}
