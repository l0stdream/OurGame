package main.Player;

import main.backpack.Backpack;
import main.items.Item;
import main.items.Weapon;
import main.items.WeaponRegistry;

import java.util.HashMap;
import java.util.Map;

public class Player {
    private static int health = 100;
    private static int maxHealth = 100;
    private final Backpack backpack = new Backpack();
    private int level = 1;
    private int experience = 0;
    private int experienceToNextLevel = 100;
    private Weapon equippedWeapon;
    private int statPoints = 0;

    //stat
    private int strength = 3;
    private int intelligence = 5;
    private int agility = 3;
    private int vitality = 3;
    private int luck = 0;

    public int getHealth() {
        return health;
    }

    public int getLevel() {
        return level;
    }

    public boolean equipWeaponById(int id) {
        Item item = backpack.getItemById(id);
        if (item instanceof Weapon weapon) {
            equippedWeapon = weapon;
            return true;
        }
        return false;
    }


    public int getAttackBonus() {
        return equippedWeapon != null ? equippedWeapon.getAttackBonus() : 0;
    }

    public void setHealth(int health) {
        this.health = Math.min(health, maxHealth);
    }

    public void addItem(Item item) {
        backpack.addItem(item);
        System.out.println("Тебе выпало: " + item.getName());
    }

    public boolean useItem(int itemId) {
        return backpack.useItem(itemId);
    }

    public String showBackpack() {
        return backpack.showItems();
    }

    public static String getHealthStatus() {
        return health + "/" + maxHealth;
    }

    public void addExperience(int amount) {
        experience += amount;
        while (experience >= experienceToNextLevel) {
            levelUp();
        }
    }

    private void levelUp() {
        level++;
        experience -= experienceToNextLevel;
        maxHealth += 10;
        health = maxHealth;
        statPoints += 3;
        System.out.println("Поздравляю нюбик у тебя lvlUP " + level + "!");
    }

    private int getExperienceForNextLevel() {
        return level * 100;
    }

    public String getCharacterInfo() {
        return "=== Информация о персонаже ===\n" +
                "Уровень: " + level + "\n" +
                "Опыт: " + experience + "/" + getExperienceForNextLevel() + "\n" +
                "Здоровье: " + getHealthStatus() +
                "Уровень: " + level + "\n" +
                "Опыт: " + experience + "/" + experienceToNextLevel + "\n" +
                "Здоровье: " + health + "/" + maxHealth + "\n" +
                "Очки характеристик: " + statPoints + "\n" +
                "Сила: " + strength + "\n" +
                "Ловкость: " + agility + "\n" +
                "Выносливость: " + vitality + "\n" +
                "Интеллект: " + intelligence + "\n" +
                "Удача: " + luck;
    }

    private boolean levelUpNotification = false;

    public boolean hasLevelUpNotification() {
        return levelUpNotification;
    }

    public void clearLevelUpNotification() {
        levelUpNotification = false;
    }

    public String showAvailableWeapons() {
        StringBuilder sb = new StringBuilder("Доступное оружие:\n");
        backpack.getItems().forEach((id, count) -> {
            Item item = backpack.getItemById(id);
            if (item instanceof Weapon weapon) {
                sb.append("- ID: ").append(id).append(", ").append(weapon.getName())
                        .append(", Атака: +").append(weapon.getAttackBonus())
                        .append(", Количество: ").append(count).append("\n");
            }
        });
        return sb.length() > 0 ? sb.toString() : "У вас нет доступного оружия.";
    }

    public void allocateStatPoint(int choice) {
        if (statPoints > 0) {
            switch (choice) {
                case 1 -> strength++;
                case 2 -> agility++;
                case 3 -> vitality++;
                case 4 -> intelligence++;
                case 5 -> luck++;
                default -> {
                    System.out.println("Некорректный выбор.");
                    return;
                }
            }
            statPoints--;
            System.out.println("Характеристика улучшена!");
        } else {
            System.out.println("У вас нет очков характеристик!");
        }
    }
}






