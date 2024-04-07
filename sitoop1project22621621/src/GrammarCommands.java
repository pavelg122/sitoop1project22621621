
public interface GrammarCommands {
    void list();
    void print(long id);
    void save(long id, String fileName);
    void addRule(long id, String rule);
    void removeRule(long id, int number) throws Exception;
    //void union(int id1, int id2);
    //void concat(int id1, int id2);
   // void chomsky(int id);
    //void cyk(int id);
    //void iter(int id);
    //void empty(int id);
    //void chomskify(int id);
}
