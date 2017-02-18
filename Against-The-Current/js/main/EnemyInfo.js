class EnemyInfo {
    constructor() {
        this.sprite = Dakra.backGroup.create(0, 0, 'chars');
        this.sprite.scale.setTo(2, 2);

        this.sprite.animations.add('enemy1', Phaser.Animation
            .generateFrameNames('char-1-godown-', 1, 3, '.png', 1), 10, true);
        this.sprite.animations.add('enemy2', Phaser.Animation
            .generateFrameNames('char-2-godown-', 1, 3, '.png', 1), 10, true);
        this.sprite.animations.add('enemy3', Phaser.Animation
            .generateFrameNames('char-3-godown-', 1, 3, '.png', 1), 10, true);
        this.sprite.animations.add('enemy4', Phaser.Animation
            .generateFrameNames('char-4-godown-', 1, 3, '.png', 1), 10, true);
        this.sprite.animations.add('enemy5', Phaser.Animation
            .generateFrameNames('char-5-godown-', 1, 3, '.png', 1), 10, true);

        this.text = Dakra.game.add.text(800, 375, '', {
            font: '18px Courier',
            fill: 'white'
        });
        this.stop();
    }

    stop() {
        this.text.kill();
        this.sprite.kill();
    }

    revive(enemy) {
        this.enemy = enemy;
        this.text.revive();
        this.sprite.reset(830, 300);
        this.sprite.animations.play('enemy' + this.enemy.TYPE);
    }

    update() {
        var HP = Math.floor(this.enemy.sprite.health);
        if (HP < 0) HP = 0;
        this.text.text = 'Coin  = ' + this.enemy.COIN +
            '\nHP    = ' + HP +
            '\nArmor = ' + this.enemy.armor +
            '\nSpeed = ' + this.enemy.movementSpeed;
        if (HP == 0) this.sprite.animations.stop();
    }
}
