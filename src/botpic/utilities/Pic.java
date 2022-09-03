package botpic.utilities;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Pic {
	
	private int approver_id;
	private int bit_flags;
	private String created_at; //fecha
	private int down_score;
	private int fav_count;
	private String file_ext;
	private int file_size;
	private String file_url;
	private boolean has_active_children;
	private boolean has_children;
	private boolean	has_large;
	private boolean has_visible_children;
	private int id;
	private int image_height;
	private int image_width;
	private boolean is_banned;
	private boolean is_deleted;
	private boolean is_flagged;
	private boolean is_note_locked;
	private boolean is_pending;
	private boolean is_rating_locked;
	private boolean is_status_locked;
	private String large_file_url;  // url a la imagen directo
	private String last_commented_at;
	private String last_comment_bumped_at;
	private String last_noted_at;
	private String md5;
	private int parent_id;
	private int pixiv_id;
	private String pool_string;
	private String preview_file_url;
	private String rating; //e: explitcit, s: safe, q: questinable
	private int score;
	private String source;
	private int tag_count;
	private int tag_count_artist;
	private int tag_count_character;
	private int tag_count_copyright;
	private int tag_count_general;
	private int tag_count_meta;
	private String tag_string;
	private String tag_string_artist;
	private String tag_string_character;
	private String tag_string_copyright;
	private String tag_string_general;
	private String tag_string_meta;
	private String updated_at; //fecha
	private int	uploader_id;
	private int up_score;
	private String path; // ubicacion dentro de la pc



	public Pic(int approver_id, int bit_flags, String created_at, int down_score, int fav_count, String file_ext,
			int file_size, String file_url, boolean has_active_children, boolean has_children, boolean has_large,
			boolean has_visible_children, int id, int image_height, int image_width, boolean is_banned,
			boolean is_deleted, boolean is_flagged, boolean is_note_locked, boolean is_pending,
			boolean is_rating_locked, boolean is_status_locked, String large_file_url, String last_commented_at,
			String last_comment_bumped_at, String last_noted_at, String md5, int parent_id, int pixiv_id,
			String pool_string, String preview_file_url, String rating, int score, String source, int tag_count,
			int tag_count_artist, int tag_count_character, int tag_count_copyright, int tag_count_general,
			int tag_count_meta, String tag_string, String tag_string_artist, String tag_string_character,
			String tag_string_copyright, String tag_string_general, String tag_string_meta, String updated_at,
			int uploader_id, int up_score) {
	//	String path
		super();
		this.approver_id = approver_id;
		this.bit_flags = bit_flags;
		this.created_at = created_at;
		this.down_score = down_score;
		this.fav_count = fav_count;
		this.file_ext = file_ext;
		this.file_size = file_size;
		this.file_url = file_url;
		this.has_active_children = has_active_children;
		this.has_children = has_children;
		this.has_large = has_large;
		this.has_visible_children = has_visible_children;
		this.id = id;
		this.image_height = image_height;
		this.image_width = image_width;
		this.is_banned = is_banned;
		this.is_deleted = is_deleted;
		this.is_flagged = is_flagged;
		this.is_note_locked = is_note_locked;
		this.is_pending = is_pending;
		this.is_rating_locked = is_rating_locked;
		this.is_status_locked = is_status_locked;
		this.large_file_url = large_file_url;
		this.last_commented_at = last_commented_at;
		this.last_comment_bumped_at = last_comment_bumped_at;
		this.last_noted_at = last_noted_at;
		this.md5 = md5;
		this.parent_id = parent_id;
		this.pixiv_id = pixiv_id;
		this.pool_string = pool_string;
		this.preview_file_url = preview_file_url;
		this.rating = rating;
		this.score = score;
		this.source = source;
		this.tag_count = tag_count;
		this.tag_count_artist = tag_count_artist;
		this.tag_count_character = tag_count_character;
		this.tag_count_copyright = tag_count_copyright;
		this.tag_count_general = tag_count_general;
		this.tag_count_meta = tag_count_meta;
		this.tag_string = tag_string;
		this.tag_string_artist = tag_string_artist;
		this.tag_string_character = tag_string_character;
		this.tag_string_copyright = tag_string_copyright;
		this.tag_string_general = tag_string_general;
		this.tag_string_meta = tag_string_meta;
		this.updated_at = updated_at;
		this.uploader_id = uploader_id;
		this.up_score = up_score;
	//	this.path = path;
	}
	
	
	
	
	public boolean download() throws IOException {
		 String path = this.checkPath();
		 try {
			 URL url = new URL(this.getLarge_file_url());
			 InputStream in = new BufferedInputStream(url.openStream());
			 ByteArrayOutputStream out = new ByteArrayOutputStream();
			 byte[] buf = new byte[1024];
			 int n = 0;
			 while (-1!=(n=in.read(buf)))
			 {
			    out.write(buf, 0, n);
			 }
			 out.close();
			 in.close();
			 byte[] response = out.toByteArray();
			 path = path + this.getId() + "." + this.getFile_ext();
			 FileOutputStream fos = new FileOutputStream(path);
			 fos.write(response);
			 fos.close();
			 this.setPath(path);
			 return true;
		} catch (MalformedURLException error) {
            // Output expected MalformedURLExceptions.
            System.out.println("Hubo un error en la URL de la imagen al descargarla");
            return false;
        } catch (Exception exception) {
            // Output unexpected Exceptions.
            System.out.println("Hubo un error al descargar la imagen");
            return false;
        }
		
	}
	
	private String checkPath() {
		path = this.absolutPath + File.separator;
		
		if (this.getRating().contains("s")) {
			return path + "safe"+ File.separator;
		}
		if (this.getRating().contains("q")) {
			return path + "questionable"+ File.separator;	
		}
		if (this.getRating().contains("e")) {
			return path + "explicit"+ File.separator;
		}
		if (this.getRating().contains("g")) {
			return path + "general"+ File.separator;
		}
		return path + "others"+ File.separator;
	}
	
	
	
	
	
	
	
	
	
	
	public String getAbsolutPath() {
		return absolutPath;
	}
	public void setAbsolutPath(String absolutPath) {
		this.absolutPath = absolutPath;
	}
	private String absolutPath;	
	
	public int getApprover_id() {
		return approver_id;
	}
	public void setApprover_id(int approver_id) {
		this.approver_id = approver_id;
	}
	public int getBit_flags() {
		return bit_flags;
	}
	public void setBit_flags(int bit_flags) {
		this.bit_flags = bit_flags;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public int getDown_score() {
		return down_score;
	}
	public void setDown_score(int down_score) {
		this.down_score = down_score;
	}
	public int getFav_count() {
		return fav_count;
	}
	public void setFav_count(int fav_count) {
		this.fav_count = fav_count;
	}
	public String getFile_ext() {
		return file_ext;
	}
	public void setFile_ext(String file_ext) {
		this.file_ext = file_ext;
	}
	public int getFile_size() {
		return file_size;
	}
	public void setFile_size(int file_size) {
		this.file_size = file_size;
	}
	public String getFile_url() {
		return file_url;
	}
	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}
	public boolean isHas_active_children() {
		return has_active_children;
	}
	public void setHas_active_children(boolean has_active_children) {
		this.has_active_children = has_active_children;
	}
	public boolean isHas_children() {
		return has_children;
	}
	public void setHas_children(boolean has_children) {
		this.has_children = has_children;
	}
	public boolean isHas_large() {
		return has_large;
	}
	public void setHas_large(boolean has_large) {
		this.has_large = has_large;
	}
	public boolean isHas_visible_children() {
		return has_visible_children;
	}
	public void setHas_visible_children(boolean has_visible_children) {
		this.has_visible_children = has_visible_children;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getImage_height() {
		return image_height;
	}
	public void setImage_height(int image_height) {
		this.image_height = image_height;
	}
	public int getImage_width() {
		return image_width;
	}
	public void setImage_width(int image_width) {
		this.image_width = image_width;
	}
	public boolean isIs_banned() {
		return is_banned;
	}
	public void setIs_banned(boolean is_banned) {
		this.is_banned = is_banned;
	}
	public boolean isIs_deleted() {
		return is_deleted;
	}
	public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}
	public boolean isIs_flagged() {
		return is_flagged;
	}
	public void setIs_flagged(boolean is_flagged) {
		this.is_flagged = is_flagged;
	}
	public boolean isIs_note_locked() {
		return is_note_locked;
	}
	public void setIs_note_locked(boolean is_note_locked) {
		this.is_note_locked = is_note_locked;
	}
	public boolean isIs_pending() {
		return is_pending;
	}
	public void setIs_pending(boolean is_pending) {
		this.is_pending = is_pending;
	}
	public boolean isIs_rating_locked() {
		return is_rating_locked;
	}
	public void setIs_rating_locked(boolean is_rating_locked) {
		this.is_rating_locked = is_rating_locked;
	}
	public boolean isIs_status_locked() {
		return is_status_locked;
	}
	public void setIs_status_locked(boolean is_status_locked) {
		this.is_status_locked = is_status_locked;
	}
	public String getLarge_file_url() {
		return large_file_url;
	}
	public void setLarge_file_url(String large_file_url) {
		this.large_file_url = large_file_url;
	}
	public String getLast_commented_at() {
		return last_commented_at;
	}
	public void setLast_commented_at(String last_commented_at) {
		this.last_commented_at = last_commented_at;
	}
	public String getLast_comment_bumped_at() {
		return last_comment_bumped_at;
	}
	public void setLast_comment_bumped_at(String last_comment_bumped_at) {
		this.last_comment_bumped_at = last_comment_bumped_at;
	}
	public String getLast_noted_at() {
		return last_noted_at;
	}
	public void setLast_noted_at(String last_noted_at) {
		this.last_noted_at = last_noted_at;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}
	public int getPixiv_id() {
		return pixiv_id;
	}
	public void setPixiv_id(int pixiv_id) {
		this.pixiv_id = pixiv_id;
	}
	public String getPool_string() {
		return pool_string;
	}
	public void setPool_string(String pool_string) {
		this.pool_string = pool_string;
	}
	public String getPreview_file_url() {
		return preview_file_url;
	}
	public void setPreview_file_url(String preview_file_url) {
		this.preview_file_url = preview_file_url;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public int getTag_count() {
		return tag_count;
	}
	public void setTag_count(int tag_count) {
		this.tag_count = tag_count;
	}
	public int getTag_count_artist() {
		return tag_count_artist;
	}
	public void setTag_count_artist(int tag_count_artist) {
		this.tag_count_artist = tag_count_artist;
	}
	public int getTag_count_character() {
		return tag_count_character;
	}
	public void setTag_count_character(int tag_count_character) {
		this.tag_count_character = tag_count_character;
	}
	public int getTag_count_copyright() {
		return tag_count_copyright;
	}
	public void setTag_count_copyright(int tag_count_copyright) {
		this.tag_count_copyright = tag_count_copyright;
	}
	public int getTag_count_general() {
		return tag_count_general;
	}
	public void setTag_count_general(int tag_count_general) {
		this.tag_count_general = tag_count_general;
	}
	public int getTag_count_meta() {
		return tag_count_meta;
	}
	public void setTag_count_meta(int tag_count_meta) {
		this.tag_count_meta = tag_count_meta;
	}
	public String getTag_string() {
		return tag_string;
	}
	public void setTag_string(String tag_string) {
		this.tag_string = tag_string;
	}
	public String getTag_string_artist() {
		return tag_string_artist;
	}
	public void setTag_string_artist(String tag_string_artist) {
		this.tag_string_artist = tag_string_artist;
	}
	public String getTag_string_character() {
		return tag_string_character;
	}
	public void setTag_string_character(String tag_string_character) {
		this.tag_string_character = tag_string_character;
	}
	public String getTag_string_copyright() {
		return tag_string_copyright;
	}
	public void setTag_string_copyright(String tag_string_copyright) {
		this.tag_string_copyright = tag_string_copyright;
	}
	public String getTag_string_general() {
		return tag_string_general;
	}
	public void setTag_string_general(String tag_string_general) {
		this.tag_string_general = tag_string_general;
	}
	public String getTag_string_meta() {
		return tag_string_meta;
	}
	public void setTag_string_meta(String tag_string_meta) {
		this.tag_string_meta = tag_string_meta;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	public int getUploader_id() {
		return uploader_id;
	}
	public void setUploader_id(int uploader_id) {
		this.uploader_id = uploader_id;
	}
	public int getUp_score() {
		return up_score;
	}
	public void setUp_score(int up_score) {
		this.up_score = up_score;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	
	
}
