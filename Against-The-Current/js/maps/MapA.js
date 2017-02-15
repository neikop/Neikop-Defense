class MapA extends Map {
    constructor() {
        var arrayMap = [
            [0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0],
            [0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0],
            [2, 2, 2, 2, 2, 2, 2, 0, 1, 2, 2, 2, 2, 0, 0],
            [1, 0, 0, 0, 0, 0, 2, 0, 0, 2, 0, 0, 2, 1, 0],
            [0, 0, 1, 0, 1, 0, 2, 0, 0, 2, 1, 0, 2, 0, 1],
            [0, 0, 2, 2, 2, 2, 2, 1, 0, 2, 0, 0, 2, 0, 0],
            [0, 0, 2, 0, 0, 0, 0, 0, 1, 2, 0, 0, 2, 0, 0],
            [1, 0, 2, 0, 0, 1, 0, 0, 0, 2, 0, 1, 2, 0, 0],
            [0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0, 2, 1, 0],
            [0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 2, 0, 1],
            [0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 2, 0, 0]
        ];
        var arrayPoint = [];
        arrayPoint.push([
            new Phaser.Point(2, 0),
            new Phaser.Point(2, 6),
            new Phaser.Point(5, 6),
            new Phaser.Point(5, 2),
            new Phaser.Point(8, 2),
            new Phaser.Point(8, 9),
            new Phaser.Point(2, 9),
            new Phaser.Point(2, 12),
            new Phaser.Point(10, 12)
        ]);
        super(arrayMap, arrayPoint);
    }
}
