class Player {
    private String name;
    private char disk;

    public Player(String name, char disk) {
        this.name = name;
        this.disk = disk;
    }

    public String getName() {
        return name;
    }

    public char getDisk() {
        return disk;
    }
}