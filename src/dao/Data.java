package dao;

import java.util.List;

import dto.Player;

/**
 * 数据持久层接口
 * @author Administrator
 *
 */
public interface Data {

	/**
	 * 读取数据
	 * @return
	 */
	List<Player> loadData();
	
	/**
	 * 存储数据
	 * @param players
	 */
	void saveData(Player players);
}
