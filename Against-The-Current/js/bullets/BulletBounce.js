class BulletBounce {
    constructor(enemyOne, enemyTwo, tower) {
        this.sprite = Dakra.bulletBounceGroup.create(
            enemyOne.sprite.position.x, enemyOne.sprite.position.y, 'bullets', 'beam-3.png');
        this.sprite.father = this;
        this.sprite.anchor.setTo(0, 1);
        this.sprite.checkWorldBounds = true;
        this.sprite.outOfBoundsKill = true;
        // this.sprite.kill();
        this.sprite.rotation = Dakra.game.physics.arcade.angleBetween(enemyOne.sprite, enemyTwo.sprite);

        this.sprite.body.velocity.x = Math.cos(this.sprite.rotation) * tower.BULLET_SPEED;
        this.sprite.body.velocity.y = Math.sin(this.sprite.rotation) * tower.BULLET_SPEED;

        this.damage = tower.DAMAGE;
        this.slow = tower.SLOW;
        this.armorPen = tower.ARMOR_PEN;
    }

    // revive(enemyOne, enemyTwo, tower) {
    //     this.sprite.reset(enemyOne.sprite.position.x, enemyOne.sprite.position.y);
    //     this.sprite.rotation = Dakra.game.physics.arcade.angleBetween(enemyOne.sprite, enemyTwo.sprite);
    //     this.sprite.body.velocity.x = Math.cos(this.sprite.rotation) * tower.BULLET_SPEED;
    //     this.sprite.body.velocity.y = Math.sin(this.sprite.rotation) * tower.BULLET_SPEED;
    //
    //     this.damage = tower.DAMAGE;
    //     this.slow = tower.SLOW;
    //     this.armorPen = tower.ARMOR_PEN;
}
