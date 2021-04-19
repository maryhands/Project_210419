package calender;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class MailBookDao {
	Connection 			con 	= null;
	PreparedStatement 	pstmt 	= null;
	ResultSet 			rs 		= null;
	DBConnectionMgr     dbMgr   = null;
	public MailBookDao() {
		dbMgr = DBConnectionMgr.getInstance();
		try {
			con = dbMgr.getConnection();
		} catch (Exception e) {
			System.out.println("오라클 서버 연결 실패!!!");
			e.printStackTrace();
		}
	}
	//콤보박스에 담길 시도 정보 조회하기 구현
	/***********************************************************************************
	 * 쿼리문
	 * SELECT '전체' zdo FROM dual
	   UNION ALL
	   SELECT distinct(zdo) FROM zipcode_t
       ORDER BY zdo asc
	 * @return String[]
	 * 전체 경기 강원 경북........
	 **********************************************************************************/
	public String[] getReceiver() {
		//원격에 있는 오라클 서버에 접속하기 위해 DBConnectionMgr객체 생성하기
		//콤보 박스에 도에 대한 정보를 가져오기
		String mails[] = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM save_rec_email ");
		try {
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			Vector<String> v = new Vector<>();
			while(rs.next()) {
				System.out.println("오류");
				String email = rs.getString("email");
				v.add(email);
			}
			for(String email1:v) {
				System.out.println(email1);
			}
			mails = new String[v.size()];
			v.copyInto(mails);
		} catch(SQLException se) {
			System.out.println("[[query]] : "+sql.toString());
		} catch (Exception e) {
			System.out.println("Exceptioin : "+e.toString());
		} finally {
			//사용한 자원 반납하기
			//이걸 상습적으로 안하면 오라클에서 세션을 닫아버린다. -자바 튜닝팀의 권장사항.
			//닫을 때는 열린것의 역순으로 닫아준다.
			/*
			 * con
			 * pstmt = con.preparedStatement();
			 * rs = pstmt.executeQuery()
			 */
			dbMgr.freeConnection(con, pstmt, rs);
		}
		return mails;
	}

	public static void main(String args[]) {
		MailBookDao mbd = new MailBookDao();
		mbd.getReceiver();

	}
}
