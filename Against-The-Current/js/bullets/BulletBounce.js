class BulletBounce {
    constructor(enemyOne, enemyTwo, tower, number) {
        this.sprite = Dakra.bulletBounceGroup.create(
            enemyOne.sprite.position.x, enemyOne.sprite.position.y, 'bullets', 'beam-3.png');
        this.sprite.father = this;
        this.sprite.anchor.setTo(0, 0.5);
        this.sprite.checkWorldBounds = true;
        this.sprite.outOfBoundsKill = true;

        this.sprite.rotation = Dakra.game.physics.arcade.angleBetween(enemyOne.sprite, enemyTwo.sprite);

        this.enemyTwo = enemyTwo;
        this.tower = tower;
        var x = enemyOne.sprite.position.x - enemyTwo.sprite.position.x;
        var y = enemyOne.sprite.position.y - enemyTwo.sprite.position.y;
        var distance = Math.sqrt(x * x + y * y);
        var rate = distance / 30;
        this.sprite.scale.setTo(rate, 1);

        this.damage = tower.DAMAGE;
        this.slow = tower.SLOW;
        this.armorPen = tower.ARMOR_PEN;

        var min = 1000;
        var target;
        for (var i = 0; i < Dakra.enemies.length; i++) {
            if (!Dakra.enemies[i].sprite.alive) continue;
            var x = this.enemyTwo.sprite.position.x - Dakra.enemies[i].sprite.position.x;
            var y = this.enemyTwo.sprite.position.y - Dakra.enemies[i].sprite.position.y;
            var distance = Math.sqrt(x * x + y * y);
            if (distance < min & distance != 0) {
                min = distance;
                target = Dakra.enemies[i];
            }
        }
        if (number == 1)
            new BulletBounce(this.enemyTwo, target, this.tower, 0);
    }

    update() {
        if (this.counter === undefined) this.counter = 0;
        this.counter++;
        if (this.counter == 2) {
            this.enemyTwo.beShot(this);
            this.sprite.kill();
        }
    }
}
