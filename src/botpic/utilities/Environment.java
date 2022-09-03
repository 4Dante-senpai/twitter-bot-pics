package botpic.utilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;

import botpic.Main;

public class Environment {
	private String path;


	public Environment() throws URISyntaxException {
		//El constructor ya crea las carpetas
		//Se crean las carpetas en el lugar del script

		this.path = null;

		CodeSource codeSource = Main.class.getProtectionDomain().getCodeSource();
		File jarFile = new File(codeSource.getLocation().toURI().getPath());
		String pathTotal = jarFile.getParentFile().getPath();


		File pathSafe = new File(pathTotal + File.separator + "safe" );
		File pathQuest = new File(pathTotal + File.separator + "questionable" );
		File pathExplicit = new File(pathTotal + File.separator + "explicit" );
		File pathGeneral = new File(pathTotal + File.separator + "general" );
		File pathOthers = new File(pathTotal + File.separator + "others" );

		if (!pathSafe.exists()){
			pathSafe.mkdirs();
		}
		if (!pathQuest.exists()){
			pathQuest.mkdirs();
		}
		if (!pathExplicit.exists()){
			pathExplicit.mkdirs();
		}
		if (!pathGeneral.exists()){
			pathGeneral.mkdirs();
		}
		if (!pathOthers.exists()){
			pathOthers.mkdirs();
		}

		this.path = pathTotal;
		createDB();
		}


	private void createDB() {
		String dbName ="jdbc:sqlite:" + this.path + File.separator + "imagenes.db";
		
		 try (Connection conn = DriverManager.getConnection(dbName)) {
	            if (conn != null) {
//	                DatabaseMetaData meta = conn.getMetaData();
//	                System.out.println("The driver name is " + meta.getDriverName());
//	                System.out.println("A new database has been created.");
	                createTable(dbName);
	                conn.close();
	            }
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }	
		 
		
	}
	
    private static void createTable(String dbName) {
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS imagenes (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	approver_id integer,\n"
                + "	created_at VARCHAR(100),\n"
                + " down_score integer,\n"
                + " bit_flags integer,\n"
                + " fav_count integer,\n"
                + "	file_ext VARCHAR(5),\n"
                + "	file_url VARCHAR(255),\n"
                + "	file_size integer,\n"
                + " has_active_children integer,\n"
                + " has_children integer,\n"
                + " has_large integer,\n"
                + " has_visible_children integer,\n"
                + " image_height integer,\n"
                + " image_width integer,\n"
                + " is_banned integer,\n"
                + " is_deleted integer,\n"
                + " is_flagged integer,\n"
                + " is_note_locked integer,\n"
                + " is_pending integer,\n"
                + " is_rating_locked integer,\n"
                + " is_status_locked integer,\n"
                + "	large_file_url VARCHAR(255),\n"     
                + "	last_commented_at VARCHAR(100),\n"
                + "	last_comment_bumped_at VARCHAR(100),\n"
                + "	last_noted_at VARCHAR(100),\n"
                + "	md5 VARCHAR(100),\n"  
                + " parent_id integer,\n"
                + " pixiv_id integer,\n"
                + "	pool_string VARCHAR(255),\n"
                + "	preview_file_url VARCHAR(225),\n"
                + "	rating VARCHAR(1),\n"
                + " score integer,\n"
                + "	source VARCHAR(225),\n"
                + " tag_count integer,\n"
                + " tag_count_artist integer,\n"
                + " tag_count_character integer,\n"
                + " tag_count_copyright integer,\n"
                + " tag_count_general integer,\n"
                + " tag_count_meta integer,\n"
                + "	tag_string VARCHAR(225),\n"
                + "	tag_string_artist VARCHAR(225),\n"
                + "	tag_string_character VARCHAR(225),\n"
                + "	tag_string_copyright VARCHAR(225),\n"
                + "	tag_string_general VARCHAR(225),\n"
                + "	tag_string_meta VARCHAR(225),\n"
                + "	updated_at VARCHAR(100),\n"
                + " uploader_id integer,\n"
                + " up_score integer\n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(dbName);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insert(Pic imagen) throws SQLException {
    	PreparedStatement pstmt = null;
    	Connection conn = null;
		String sql = "INSERT INTO imagenes(approver_id, bit_flags, created_at, down_score, fav_count, file_ext,\r\n" //1-6
				+ "			file_size, file_url, has_active_children, has_children, has_large,\r\n" //6-10  +1
				+ "			has_visible_children, id, image_height, image_width, is_banned,\r\n" //11-15   +1			
				+ "			is_deleted, is_flagged, is_note_locked, is_pending,\r\n" //16-19   +1				
				+ "			is_rating_locked, is_status_locked, large_file_url, last_commented_at,\r\n"//20-23   +1				
				+ "			last_comment_bumped_at, last_noted_at, md5, parent_id, pixiv_id,\r\n" //24-28   +1			
				+ "			pool_string, preview_file_url, rating, score, source, tag_count,\r\n" //29-34   +1			
				+ "			tag_count_artist, tag_count_character, tag_count_copyright, tag_count_general,\r\n" //35-38   +1			
				+ "			tag_count_meta, tag_string, tag_string_artist, tag_string_character,\r\n" //39-42   +1			
				+ "			tag_string_copyright, tag_string_general, tag_string_meta, updated_at, uploader_id,\r\n" //43-47   +1
				+ "			 up_score) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; //48  +1
    	String dbName ="jdbc:sqlite:" + this.path + File.separator + "imagenes.db";
    	try {
    		conn = DriverManager.getConnection(dbName);  //se conecta a la ddbb
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, imagen.getApprover_id());
			pstmt.setInt(2, imagen.getBit_flags());
			pstmt.setString(3, imagen.getCreated_at());
			pstmt.setInt(4, imagen.getDown_score());
			pstmt.setInt(5, imagen.getFav_count());
			pstmt.setString(6, imagen.getFile_ext());
			pstmt.setInt(7, imagen.getFile_size());
			pstmt.setString(8, imagen.getFile_url());
			pstmt.setInt(9, imagen.isHas_active_children() ? 1: 0);
			pstmt.setInt(10, imagen.isHas_children() ? 1 : 0);
			pstmt.setInt(11, imagen.isHas_large() ? 1 : 0);
			pstmt.setInt(12, imagen.isHas_visible_children() ? 1 : 0);
			pstmt.setInt(13, imagen.getId());
			pstmt.setInt(14, imagen.getImage_height());
			pstmt.setInt(15, imagen.getImage_width());
			pstmt.setInt(16, imagen.isIs_banned() ? 1 : 0);
			pstmt.setInt(17, imagen.isIs_deleted() ? 1 : 0);
			pstmt.setInt(18, imagen.isIs_flagged() ? 1 : 0);
			pstmt.setInt(19, imagen.isIs_note_locked() ? 1 : 0);
			pstmt.setInt(20, imagen.isIs_pending() ? 1 : 0);
			pstmt.setInt(21, imagen.isIs_rating_locked() ? 1: 0);
			pstmt.setInt(22, imagen.isIs_status_locked() ? 1 : 0);
			pstmt.setString(23, imagen.getLarge_file_url());
			pstmt.setString(24, imagen.getLast_commented_at());
			pstmt.setString(25, imagen.getLast_comment_bumped_at());
			pstmt.setString(26, imagen.getLast_noted_at());
			pstmt.setString(27, imagen.getMd5());
			pstmt.setInt(28, imagen.getParent_id());
			pstmt.setInt(29, imagen.getPixiv_id());
			pstmt.setString(30, imagen.getPool_string());
			pstmt.setString(31, imagen.getPreview_file_url());
			pstmt.setString(32, imagen.getRating());
			pstmt.setInt(33, imagen.getScore());
			pstmt.setString(34, imagen.getSource());
			pstmt.setInt(35, imagen.getTag_count());
			pstmt.setInt(36, imagen.getTag_count_artist());
			pstmt.setInt(37, imagen.getTag_count_character());
			pstmt.setInt(38, imagen.getTag_count_copyright());
			pstmt.setInt(39, imagen.getTag_count_general());
			pstmt.setInt(40, imagen.getTag_count_meta());
			pstmt.setString(41, imagen.getTag_string());
			pstmt.setString(42, imagen.getTag_string_artist());
			pstmt.setString(43, imagen.getTag_string_character());
			pstmt.setString(44, imagen.getTag_string_copyright());
			pstmt.setString(45, imagen.getTag_string_general());
			pstmt.setString(46, imagen.getTag_string_meta());
			pstmt.setString(47, imagen.getUpdated_at());
			pstmt.setInt(48, imagen.getUploader_id());
			pstmt.setInt(49, imagen.getUp_score());
			pstmt.executeUpdate();
    		pstmt.close(); 
    		conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
    		if (pstmt != null) {
    			pstmt.close();
    			}
    		if (conn != null) {
    			conn.close();
    			}
    		}
    	 
    	 
    }
    
	public String getPath() {
		return path;
	}



	public void setPath(String path) {
		this.path = path;
	}
		

		
}
	


