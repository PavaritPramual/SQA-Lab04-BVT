# SQA Lab 4: Boundary Value Analysis & Robustness Testing

## 📋 ข้อมูลรายวิชาและผู้จัดทำ (Project Information)
* **วิชา:** CP353201 Software Quality Assurance [1/2569]
* **สถาบัน:** สาขาวิชาวิทยาการคอมพิวเตอร์ วิทยาลัยการคอมพิวเตอร์ มหาวิทยาลัยขอนแก่น
* **ผู้สอน:** ผศ. ดร.ชิตสุธา สุ่มเล็ก
* **ผู้จัดทำ:** ปวริศช์ ประมวล (รหัสนักศึกษา: 673380278-9)
* **วันที่รายงาน:** 18 กรกฎาคม 2569

---

## 🎯 ภาพรวมของการทดสอบ (System Under Test Overview)
โปรเจกต์นี้เป็นการออกแบบและทดสอบระดับหน่วย (Unit Testing) สำหรับคลาส `HealthIndexScore` ซึ่งทำหน้าที่คำนวณระดับความฟิตของร่างกายจาก 3 ปัจจัยหลัก:
1. **VO2 Max (สมรรถภาพหัวใจและปอด):** รับค่าเพื่อประเมินเป็นคะแนน 0 - 5
2. **Resting Heart Rate - RHR (อัตราการเต้นของหัวใจขณะพัก):** รับค่า 40 - 220 ครั้ง/นาที ประเมินเป็นคะแนน 1, 3 หรือ 5
3. **Heart Rate Recovery - HRR (อัตราการฟื้นตัวใน 1 นาที):** ประเมินจากอัตราที่ลดลง เป็นคะแนน 1, 3, 4 หรือ 5

คะแนนรวมจากทั้ง 3 ปัจจัย (Health Index Score) จะถูกนำมาจัดระดับดังนี้:
* **0 - 5 คะแนน:** Poor
* **6 - 11 คะแนน:** Standard
* **12 - 15 คะแนน:** Excellent

---

## 🏗 โครงสร้างคลังข้อมูล (Repository Structure)
คลังข้อมูลนี้แบ่งออกเป็น 2 ส่วนหลัก เพื่อทดสอบด้วยกลยุทธ์ที่แตกต่างกัน:

```text
.
├── Lab4-1/                                 # การทดสอบด้วย Normal Boundary Value Testing
│   └── HealthIndexScore/                   # โครงสร้าง Java Project แบบมาตรฐาน
│       ├── src/.../HealthIndexScore.java       # Source code หลัก
│       └── test/.../HealthIndexScoreTest.java  # โค้ดทดสอบ JUnit
├── Lab4-2/                                 # การทดสอบด้วย Robustness Testing
│   └── HealthIndexScore/                   # โครงสร้าง Maven Project
│       ├── pom.xml                             # ไฟล์จัดการ Dependency
│       ├── src/main/.../HealthIndexScore.java               # Source code หลัก
│       └── src/test/.../HealthIndexScoreRobustnessTest.java # โค้ดทดสอบ JUnit
└── Lab4_BVT.xlsx                           # ไฟล์บันทึกการออกแบบ Test Case และ Defect Log
```

---

## 🧪 กลยุทธ์การทดสอบ (Testing Strategies)
1. **Normal Boundary Value Testing (Lab 4-1):**
   * ทดสอบค่าขอบเขตปกติ (min, min+, nom, max-, max) ของตัวแปรแต่ละตัว
   * เพื่อยืนยันว่าโปรแกรมสามารถจัดการข้อมูลที่อยู่ในขอบเขตที่ถูกต้องได้อย่างสมบูรณ์

2. **Robustness Testing (Lab 4-2):**
   * ขยายการทดสอบให้ครอบคลุมค่าขอบเขตที่ผิดปกติ (min-, max+)
   * เพื่อประเมินความทนทานของซอฟต์แวร์เมื่อได้รับ Input ที่อยู่นอกเหนือช่วงที่กำหนด และดูการจัดการข้อผิดพลาด (Error Handling)

---

## 📊 สรุปผลการทดสอบ (Test Execution Summary)
การรันโค้ดทดสอบแบบอัตโนมัติ (Automated Testing) ได้ผลลัพธ์ดังตารางด้านล่าง (อ้างอิงข้อมูลฉบับเต็มจากไฟล์ `Lab4_BVT.xlsx`)

| กลยุทธ์การทดสอบ (Test Strategy) | จำนวน Test Case ทั้งหมด | ✔️ ผ่าน (Pass) | ❌ ไม่ผ่าน (Fail) | ⚠️ ข้าม (Block/No Run) |
| :--- | :---: | :---: | :---: | :---: |
| **Normal Boundary Value Testing** | 13 | 12 | 1 | 0 |
| **Robustness Testing** | 19 | 18 | 1 | 0 |
| **รวมทั้งหมด (Total)** | **32** | **30** | **2** | **0** |

---

## 🚨 ปัญหาที่พบและยังไม่ได้รับการแก้ไข (Open Defects / Known Issues)
จากการทดสอบพบข้อบกพร่องที่ส่งผลกระทบต่อความถูกต้องของ Business Logic โดยตรง ซึ่งอยู่ในระดับความรุนแรง **High Severity** และต้องได้รับการแก้ไขใน Source code หลัก:

* **Defect ID:** N1
* **Severity & Priority:** 🔴 High / 🔴 High
* **Test Case ที่เกี่ยวข้อง:** TC002 (หมวด Normal BVT) และอาจเชื่อมโยงกับการคำนวณใน Robustness Testing
* **รายละเอียดความผิดพลาด:** 
  เมื่อทำการส่ง Input เป็น `VO2Max=52.0`, `RHR=41`, `HRR=15`
  * **ผลลัพธ์ที่คาดหวัง (Expected):** คะแนนจะได้ (4 + 3 + 3) = 10 คะแนน ซึ่งควรจัดอยู่ในเกณฑ์ **`STANDARD`**
  * **ผลลัพธ์ที่ได้จริง (Actual):** โปรแกรมคำนวณและคืนค่ากลับมาเป็น **`EXCELLENT`**
* **สาเหตุเบื้องต้นที่คาดการณ์:** มีข้อบกพร่องในตรรกะการรวมคะแนน หรือเงื่อนไข `if-else` ในคลาส `HealthIndexScore` ที่กำหนดช่วงคะแนนผิดพลาด (ช่วง 12-15 สำหรับ Excellent อาจถูกประเมินผิด)
* **สถานะปัจจุบัน:** **Open** (รอการแก้ไขจากทีมพัฒนา)

---

## ⚙️ วิธีการติดตั้งและรันการทดสอบ (How to Run the Tests)

### ข้อกำหนดเบื้องต้น (Prerequisites)
* Java Development Kit (JDK) 11 หรือใหม่กว่า
* IDE เช่น Eclipse, IntelliJ IDEA หรือ VS Code
* Maven (สำหรับการทดสอบใน Lab 4-2)

### รันการทดสอบ Lab 4-1 (Normal BVT)
1. เปิด IDE และ Import โฟลเดอร์ `Lab4-1/HealthIndexScore` เป็นโปรเจกต์ Java มาตรฐาน
2. ตรวจสอบว่าได้กำหนด JUnit ไว้ใน Build Path เรียบร้อยแล้ว
3. คลิกขวาที่ไฟล์ `test/.../HealthIndexScoreTest.java` 
4. เลือก **Run As > JUnit Test**

### รันการทดสอบ Lab 4-2 (Robustness Testing)
1. เปิดโฟลเดอร์ `Lab4-2/HealthIndexScore` ใน IDE ในฐานะ **Maven Project**
2. รันคำสั่งผ่าน Terminal ที่ Root directory ของ Lab 4-2:
   ```bash
   mvn clean test
   ```
3. หรือคลิกขวาที่ไฟล์ `src/test/.../HealthIndexScoreRobustnessTest.java` และเลือก **Run As > JUnit Test**

---
*หมายเหตุ: README ฉบับนี้จัดทำขึ้นอย่างสมบูรณ์เพื่อสะท้อนสถานะความเป็นจริงของคุณภาพซอฟต์แวร์ ณ วันที่รายงาน ไม่อนุญาตให้แก้ไขสถานะ Defect จนกว่าจะมีการ Commit โค้ดที่แก้ปัญหา N1 แล้วเท่านั้น*
