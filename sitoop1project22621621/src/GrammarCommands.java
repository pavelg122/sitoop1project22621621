
public interface GrammarCommands {
    void list();
    void print(int id);
    void save(int id, String fileName);
    void addRule(int id, String rule);
    void removeRule(int id, int number);
    //void union(int id1, int id2);
    //void concat(int id1, int id2);
   // void chomsky(int id);
    //void cyk(int id);
    //void iter(int id);
    //void empty(int id);
    //void chomskify(int id);
}
