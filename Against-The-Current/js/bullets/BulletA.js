class BulletA {
    constructor(tower) {
        this.sprite = Dakra.towerBulletGroup.create(0, 0, 'bullets', 'beam-1.png');
        this.sprite.anchor.setTo(0.5, 0.5);
        this.sprite.checkWorldBound = true;
        this.sprite.outOfBoundsKill = true;

        this.sprite.reset(tower.sprite.position.x, tower.sprite.position.y);
        this.sprite.rotation = tower.sprite.rotation;
        this.sprite.body.velocity.x = Math.cos(this.sprite.rotation) * tower.BULLET_SPEED;
        this.sprite.body.velocity.y = Math.sin(this.sprite.rotation) * tower.BULLET_SPEED;
    }
}
