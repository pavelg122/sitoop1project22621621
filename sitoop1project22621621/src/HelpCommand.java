public class HelpCommand implements Command{
    @Override
    public void invoke(String[] input) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("The following commands are supported:\n");
            stringBuilder.append("open <file>       opens <file>\n");
            stringBuilder.append("close             closes currently opened file\n");
            stringBuilder.append("save              saves the currently open file\n");
            stringBuilder.append("saveas <file>     saves the currently open file in <file>\n");
            stringBuilder.append("help              prints this information\n");
            stringBuilder.append("exit              exists the program\n");
            System.out.println(stringBuilder);
    }
}
