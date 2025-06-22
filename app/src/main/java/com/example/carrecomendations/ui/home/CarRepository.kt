package com.example.carrecomendations.ui.home

import com.example.carrecomendations.R

object CarRepository {

    private val allCars = listOf(
        Car(1, "MG Cyberster", "Rp 1.688.000.000", "Ditenagai dual motor listrik, MG Cyberster mampu menghasilkan tenaga hingga 536 HP dan torsi puncak 725 Nm. Dengan baterai berkapasitas 77 kWh, mobil ini memiliki jangkauan hingga 503 km (NEDC) dalam sekali pengisian daya. Akselerasinya sangat impresif, mampu melaju dari 0–100 km/jam hanya dalam waktu 3,2 detik.", R.drawable.mg_cyberster, "Convertible"),
        Car(2, "Porsche 911 GT3 RS", "Rp 13.000.000.000", "Porsche 911 GT3 RS (2024) adalah varian tertinggi dari lini 911 yang dirancang khusus untuk performa maksimal di lintasan balap, namun tetap legal digunakan di jalan raya. Mobil ini menggabungkan teknologi motorsport terkini dengan desain aerodinamis canggih dan konstruksi ringan yang ekstrem.", R.drawable.porche, "Convertible"),
        Car(3, "Toyota New Hilux Rangga", "Rp 188.700.000", "Toyota Hilux Rangga memiliki dua pilihan mesin. Pertama adalah mesin bensin 1TR-FE 2L yang mampu menghasilkan tenaga maksimal 139 ps dan torsi 183 Nm. Kedua adalah mesin diesel 2GD-FTV 2,4L yang menghasilkan daya maksimal 149 ps dan torsi 343 Nm.", R.drawable.toyota, "Pickup"),
        Car(4, "Mitsubishi All New Triton", "Rp 307.200.000", "Mobil ini dibekali mesin diesel turbo 2.4L 4N16 Euro 4 yang lebih bertenaga dan lebih efisien. Fitur keselamatannya juga lengkap, seperti Rise Body, side impact beam, dual SRS airbag dan driver knee airbag, hill start assist, ABS, EBD, serta Active Stability dan Traction Control.", R.drawable.mitsubishi, "Pickup"),
        Car(5, "Bentley Continental", "Rp 8.500.000.000", "Bentley Continental GT adalah mobil grand tourer mewah yang menggabungkan performa tinggi dan kenyamanan kelas atas. Ditenagai mesin V8 atau W12 twin-turbo, mobil ini menawarkan akselerasi cepat dan handling yang halus. Interiornya mewah dengan bahan berkualitas seperti kulit dan kayu, serta dilengkapi fitur teknologi modern. Desain eksteriornya elegan dan sporty.", R.drawable.bentley, "Convertible"),
        Car(6, "MC Laren F1", "Rp 17.400.000.000", "SUV tangguh dan mewah dengan desain gagah, mesin diesel 2.4L turbo, dan fitur keselamatan canggih seperti kamera 360°, Blind Spot Warning, dan Forward Collision Mitigation. Nyaman, stylish, dan siap untuk segala medan.", R.drawable.mc_laren, "Supercar"),
        Car(7, "Pagani Huayra", "Rp 21.260.000.000", "SUV tangguh dan mewah dengan desain gagah, mesin diesel 2.4L turbo, dan fitur keselamatan canggih seperti kamera 360°, Blind Spot Warning, dan Forward Collision Mitigation. Nyaman, stylish, dan siap untuk segala medan.", R.drawable.pagani, "Supercar"),
        Car(8, "BYD Dolphin", "Rp 429.000.000", "BYD Dolphin 2025 adalah 5 Seater Hatchback yang tersedia dalam daftar harga Rp 369 - 429 Juta di Indonesia. Mobil ini memiliki ground clearance 130 mm dengan dimensi sebagai berikut: 4290 mm L x 1770 mm W x 1570 mm H. Pesaing terdekat BYD Dolphin adalah 2, Yaris, City Hatchback dan Binguo EV.", R.drawable.byd, "Hatchback"),
        Car(9, "Mazda 2", "Rp 371.000.000", "Mobil ini memiliki ground clearance 152 mm dengan dimensi sebagai berikut: 4080 mm L x 1695 mm W x 1495 mm H. Lebih dari 22 pengguna telah memberikan penilaian untuk 2 berdasarkan fitur, jarak tempuh, kenyamanan tempat duduk dan kinerja mesin. Pesaing terdekat Mazda 2 adalah Yaris, City Hatchback, Binguo EV dan Dolphin.", R.drawable.mazda, "Hatchback"),
        Car(10,"Daihatsu gran max", "Rp 163.550.000", "Daihatsu Gran Max Pick Up memiliki 2 pilihan mesin. Yang termurah berkapasitas 1.3L dengan mesin berkode K3-DE, DOHC 4 Silinder Segaris. Mesin ini mampu menghasilkan daya maksimal 88 ps dan torsi maksimal 115 Nm. Dimensi total GranMax yaitu 4.195 x 1.665 x 1.850 mm dengan kapasitas tempat duduk 3 penumpang. Ukuran baknya 2.350 x 1.585 x 300 mm yang mampu membawa muatan hingga 1,5 ton.", R.drawable.daihatsu, "Pickup"),
        Car(11,"Honda Brio Satya E CVT", "Rp 258.200.000", "Honda Brio tersedia dalam pilihan mesin Bensin di Indonesia Hatchback baru dari Honda hadir dalam 5 varian. Bicara soal spesifikasi mesin Honda Brio, ini ditenagai dua pilihan mesin Bensin berkapasitas 1199 cc. Brio tersedia dengan transmisi Manual and CVT tergantung variannya. Brio adalah Hatchback 5 seater dengan panjang 3810 mm, lebar 1680 mm, wheelbase 2405 mm.", R.drawable.brio, "Hatchback"),
        Car(12,"Zenvo St1", "Rp 19.600.000.000", "SUV tangguh dan mewah dengan desain gagah, mesin diesel 2.4L turbo, dan fitur keselamatan canggih seperti kamera 360°, Blind Spot Warning, dan Forward Collision Mitigation. Nyaman, stylish, dan siap untuk segala medan.", R.drawable.zenvo, "Supercar")
    )

    fun getAllCars(): List<Car> {
        return allCars
    }

    fun findCarById(id: Int): Car? {
        return allCars.find { it.id == id }
    }
}
