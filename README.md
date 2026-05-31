# Inventory-Management-System
A robust, cross-platform Automotive Inventory Management System 
# Automotive Inventory Management System & Logistics Ecosystem

## Project Overview
This repository contains a full-stack, enterprise-grade Automotive Parts Inventory Management System designed to streamline parts tracking, stock optimization, and backend logistics. Originally a legacy application, this system has been completely modernized across three distinct technical categories—Software Design and Engineering, Algorithms and Data Structures, and Databases—to demonstrate graduation-ready computer science competencies in architecture, performance optimization, and secure data handling.

---

## Core Technical Enhancements

### 📦 1. Software Design and Engineering
*   **Objective:** Eliminate architectural weaknesses and improve application maintainability.
*   **Enhancement Details:** Refactored the legacy monolithic code into a clean, modular **Model-View-Controller (MVC)** architecture. This structural separation ensures that data models, user interfaces, and business logic can be scaled independently.
*   **Reliability & Quality:** Implemented robust user input validation routines, comprehensive secure error handling, and structured unit test suites to maximize overall system stability and software reliability.

### ⚡ 2. Algorithms and Data Structures
*   **Objective:** Resolve data processing bottlenecks and optimize runtime performance under heavy system loads.
*   **Enhancement Details:** Diagnosed an inefficient legacy sorting mechanism and replaced it with a highly optimized, $O(n \log n)$ **Merge Sort** algorithm. 
*   **Impact Metrics:** This architectural change dramatically increases data scanning speeds and processing efficiency, allowing the shop inventory system to sort and filter large components and datasets smoothly without computational lag.

### 🗄️ 3. Databases
*   **Objective:** Ensure strict data integrity, eliminate redundancy, and defend against critical security vulnerabilities.
*   **Enhancement Details:** Modernized the backend database by normalizing the relational schema from First Normal Form (**1NF**) to Third Normal Form (**3NF**), minimizing data storage overhead.
*   **Security Implementation:** Fully secured the application data layer by rewriting query execution blocks using **parameterized queries**, completely mitigating risks associated with SQL injection vulnerabilities.

## Capstone Code Review Presentation
Click the image below to watch the 15-minute technical code review walking through the architecture, algorithmic optimization, and database security enhancements of this ecosystem:

[![Capstone Code Review Presentation](https://www.youtube.com/watch?v=SStnFgqhobA)
