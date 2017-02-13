class MapA {
    constructor() {
        this.arrayMap = [
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

        this.width = this.arrayMap.length;
        this.height = this.arrayMap[0].length;

        for (var w = 0; w < this.width; w++) {
            for (var h = 0; h < this.height; h++) {
                var x = this.arrayMap[w][h];
                var y = Math.random() * 4;
                Dakra.game.add.sprite(40 * w, 40 * h,
                    x == 0 ? y < 1 ? 'ground-a' : y < 2 ? 'ground-b' : y < 3 ? 'ground-c' : 'ground-d' :
                    x == 1 ? 'flower' : Math.random() < 0.5 ? 'road-e' : 'road-f');
            }
        }
        this.arrayMap.push([1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]);
        this.arrayMap.push([1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]);
        this.arrayMap.push([1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]);
    }
}
