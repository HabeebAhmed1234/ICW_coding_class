import java.util.ArrayList;
import java.util.List;

public class EntityStore {

  private List<GameEntity> entities = new ArrayList<>();

  public void add(GameEntity gameEntity) {
    entities.add(gameEntity);
  }

  public int getSize() {
    return entities.size();
  }

  public GameEntity get(int index) {
    return entities.get(index);
  }
}
