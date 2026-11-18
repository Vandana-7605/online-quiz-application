# Insider Threat Behavioral Intelligence System (ITBIS)

**Insider Threat Behavioral Intelligence System (ITBIS)** is an enterprise-grade cybersecurity platform engineered to monitor, profile, detect, and investigate risky insider behavior performed by employees with authorized system access.

---

## 🎯 Problem Statement

Traditional security defenses focus on keeping external attackers outside the network perimeter. However, **insider threats** stem from trusted users—employees, contractors, or privileged administrators—who already possess legitimate access to internal databases, source code, and confidential corporate assets. 

ITBIS solves this challenge by ingesting multi-source activity logs, establishing per-employee behavioral baselines, running statistical and Machine Learning (Isolation Forest) anomaly detection engines, computing dynamic 0–100 risk scores, and equipping security teams with role-specific dashboards, automated incident dockets, and report export capabilities.

---

## 🏛️ System Architecture

```
                                  +-------------------------+
                                  |  Next.js Frontend Client |
                                  |   (http://localhost:3000)|
                                  +------------+------------+
                                               |
                                               v  REST APIs / JWT Bearer Authorization
                                  +------------+------------+
                                  |  FastAPI Backend Server |
                                  |   (http://localhost:8000)|
                                  +-----+-------------+-----+
                                        |             |
                        +--------------+             +--------------+
                        v                                           v
         +--------------+--------------+             +--------------+--------------+
         |     PostgreSQL Relational   |             |      MongoDB Document Store |
         | (Users, Employees, Alerts,  |             | (Activity Logs, Behavioral  |
         |           Incidents)        |             |  Baselines, ML Anomalies)   |
         +-----------------------------+             +-----------------------------+
```

### Why Dual Databases?
* **PostgreSQL**: Stores structured relational data requiring strict schema constraints, foreign key relationships, and ACID compliance (User accounts, employee registries, alerts, incident dockets, and evidence notes).
* **MongoDB**: Stores high-volume, flexible JSON activity log telemetry (Logins, file transfers, USB insertions, application usage) and calculated behavioral baselines and anomaly flags.

---

## ⚡ Technology Stack

### Backend
* **Language**: Python 3.11+
* **Framework**: FastAPI (Uvicorn ASGI Web Server)
* **Relational ORM**: SQLAlchemy 2.0 (PostgreSQL driver `psycopg2-binary` with SQLite fallback)
* **Document Store**: PyMongo Client with in-memory fallback engine
* **Authentication**: JWT Tokens (Jose/JWT, 8-hour expiration)
* **Security & Hashing**: Passlib + Bcrypt
* **Analytics & Data Processing**: Pandas, NumPy
* **Machine Learning**: Scikit-Learn (Isolation Forest), Joblib
* **Report Exports**: OpenPyXL (Excel `.xlsx`), ReportLab (PDF `.pdf`)
* **Testing**: Pytest & Python Unittest framework

### Frontend
* **Framework**: Next.js 14 (App Router)
* **UI Engine**: React 18 + TypeScript
* **Styling**: Tailwind CSS (Custom SOC Dark Cyberpunk Theme)
* **HTTP Client**: Axios with Bearer token interceptor
* **Icons & Visualization**: Lucide React, Chart.js, React-Chartjs-2

### Infrastructure & Containerization
* **Containers**: Docker & Docker Compose (4-service orchestration)

---

## 🔒 Role-Based Access Control (RBAC) Matrix

ITBIS implements strict role-based access control across backend endpoints and frontend views for 4 distinct security user roles:

| Feature / Endpoint | Security Analyst | SOC Engineer | Security Manager | Admin |
| :--- | :---: | :---: | :---: | :---: |
| **Analyst Dashboard (`/dashboard/analyst`)** | ✅ | ❌ | ❌ | ✅ |
| **SOC Operations Dashboard (`/dashboard/soc`)** | ❌ | ✅ | ❌ | ✅ |
| **Manager Executive Dashboard (`/dashboard/manager`)** | ❌ | ❌ | ✅ | ✅ |
| **Admin Platform Dashboard (`/dashboard/admin`)** | ❌ | ❌ | ❌ | ✅ |
| **View Employees & Logs** | ✅ | ✅ | ✅ | ✅ |
| **Create / Edit Employees** | ❌ | ❌ | ✅ | ✅ |
| **Alert Triage & Resolution** | ✅ | ✅ | ✅ | ✅ |
| **Alert Assignment to Analysts** | ❌ | ❌ | ✅ | ✅ |
| **Create Incident Dockets** | ✅ | ✅ | ✅ | ✅ |
| **Export Excel & PDF Reports** | ❌ | ❌ | ✅ | ✅ |
| **User Administration (`/users`)** | ❌ | ❌ | ❌ | ✅ |

---

## 🖥️ Executive & Operational Dashboards

ITBIS provides **4 specialized dashboards** tailored to specific organizational security responsibilities:

1. **Security Analyst Dashboard (`/dashboard/analyst`)**:
   Focuses on tactical investigation—displays open alerts, personal investigation queue, active incident cases, and top-risk flagged employees.
2. **SOC Operations Dashboard (`/dashboard/soc`)**:
   Focuses on real-time operational posture—displays alert volume breakdown by severity, 7-day anomaly trend timeline, and active investigation counts.
3. **Security Manager Dashboard (`/dashboard/manager`)**:
   Focuses on executive oversight—displays total monitored endpoints, green-zone risk ratios, high/critical employee risk roster, compliance metrics, and 1-click Excel/PDF threat report export tools.
4. **Admin Platform Dashboard (`/dashboard/admin`)**:
   Focuses on platform administration—displays platform user count, employee count, total incidents, open incidents, role breakdown distribution, and system telemetry health.

---

## 🔍 Risk Detection & Behavioral Intelligence Workflow

```
Raw Activity Log Ingestion (MongoDB)
            ↓
Behavioral Baselines (5 Per-Employee Indicators)
            ↓
Rule-Based Anomalies (Z-Score & Set Membership) + Isolation Forest ML Detection
            ↓
Weighted Risk Scoring Engine (0 - 100 Score)
            ↓
Critical Alert In-App Escalation & Notification
            ↓
Incident Docket Creation & Timeline Investigation Docket
            ↓
Executive Threat Report Generation & Document Export (Excel / PDF)
```

### Risk Score Calculation Formula (0 - 100)
- **Behavioral Anomalies (35%)**: $\min(\text{Rule Anomalies} \times 10 + \text{ML Outliers} \times 15, 100)$
- **Privilege Misuse (25%)**: $\min(\text{Privilege Violations} \times 40, 100)$
- **Data Access Violations (20%)**: $\min(\text{Exfiltration / Device Violations} \times 50, 100)$
- **Access Pattern Deviations (10%)**: $\min(\text{Unusual Time / Resource Deviations} \times 15, 100)$
- **Historical Security Events (10%)**: $\min(\text{Total Historical Flags} \times 2, 100)$

**Risk Level Categories:**
- `Low`: Score 0.0 - 24.9
- `Medium`: Score 25.0 - 49.9
- `High`: Score 50.0 - 74.9
- `Critical`: Score 75.0 - 100.0

---

## 📊 Report Generation & Document Export System (Milestone 4, Day 33–34)

ITBIS provides an in-memory report generation and document export subsystem designed to bridge platform intelligence to external stakeholders, compliance audits, and data analysis pipelines:

### 1. Available Report Endpoints & Catalog

| Report Name | Format | Endpoint | Permitted Roles | Description & Filters |
| :--- | :--- | :--- | :--- | :--- |
| **Report Catalog** | JSON | `GET /reports/catalog` | `admin`, `security_manager`, `security_analyst` | Single source of truth listing all available reports, formats, parameters, and roles. |
| **Employee Risk Report** | PDF | `GET /reports/{employee_id}/pdf` | `admin`, `security_manager`, `security_analyst` | Individual employee risk dossier featuring overall score/100, risk level, 5 weighted factor sub-scores, and up to 10 recent anomalies. Safely handles 0 anomalies. |
| **All Risk Scores** | Excel | `GET /reports/export/risk-scores` | `admin`, `security_manager` | Bulk organizational risk scores sorted descending by Risk Score (`df.sort_values`). Supports optional `?department=` filter. |
| **Incident Docket Report** | Excel | `GET /reports/export/incidents` | `admin`, `security_manager` | Investigation incidents export with Incident ID, Employee ID, Severity, Status, Created, and Resolved. Null resolution displays strictly `"Not resolved"`. Supports optional `?status=` filter. |
| **Compliance Security Summary** | PDF | `GET /reports/compliance/pdf` | `admin`, `security_manager` | Executive security compliance audit report reusing endpoint baseline coverage, monitored endpoints, RBAC enforcement, and threat posture metrics. |
| **Executive Threat Intel (Excel)** | Excel | `GET /reports/insider-threat/excel` | `admin`, `security_manager` | Bulk tabular threat overview spreadsheet across all monitored employees. |
| **Executive Threat Intel (PDF)** | PDF | `GET /reports/insider-threat/pdf` | `admin`, `security_manager` | High-level threat intelligence table PDF report. |

### 2. Key Architecture Standards
- **In-Memory Buffering**: All PDFs and Excel spreadsheets are constructed purely in memory using `io.BytesIO()`. After building, the buffer position is rewound with `buffer.seek(0)` to prevent truncated/corrupted downloads.
- **Strict Content-Type MIME Headers**:
  - PDF: `application/pdf`
  - Excel (.xlsx): `application/vnd.openxmlformats-officedocument.spreadsheetml.sheet`
  - Content-Disposition: `attachment; filename=...`
- **Frontend Catalog Integration**: The frontend (`/reports`) dynamically queries `GET /reports/catalog` to render report cards, permitted actions, and filter controls without hardcoding report endpoints.

### 3. Automated Report Test Commands
```bash
# Run Milestone 4 Reports test suite
backend/venv/Scripts/pytest -v backend/tests/test_milestone4_reports.py

# Run all test suites across the repository
backend/venv/Scripts/pytest -v backend/tests
```


---

## ⚙️ Environment Variables & Configuration

Copy `.env.example` to `.env`:

```env
SECRET_KEY=your_secure_random_jwt_secret_key_here
ALGORITHM=HS256
ACCESS_TOKEN_EXPIRE_MINUTES=480

DATABASE_URL=postgresql://postgres:password@localhost:5432/itbis
POSTGRES_USER=postgres
POSTGRES_PASSWORD=password
POSTGRES_HOST=localhost
POSTGRES_PORT=5432
POSTGRES_DB=itbis

MONGO_URL=mongodb://localhost:27017
MONGODB_URL=mongodb://localhost:27017
MONGODB_DB_NAME=itbis

NEXT_PUBLIC_API_URL=http://localhost:8000
```

> [!IMPORTANT]
> **Large File & ML Model Policy:**
> Trained machine learning model files (`*.pkl`, `anomaly_model.pkl`) must **NOT** be committed to Git repositories and are explicitly excluded via `.gitignore`.
> The ML model should be regenerated dynamically on any environment using the training script:
> ```bash
> python backend/scripts/run_ml_detection.py
> ```

---

## 🚀 Local Development Setup

### 1. Backend Setup
```powershell
cd backend
python -m venv venv
venv\Scripts\activate
pip install -r requirements.txt
python seed.py
uvicorn app.main:app --reload --port 8000
```

### 2. Frontend Setup
```powershell
cd frontend
npm install
npm run dev
```

Application URL: `http://localhost:3000`  
API Swagger Documentation: `http://localhost:8000/docs`

---

## 🔑 Pre-Populated Demo Credentials

| Role | Username | Email | Password |
| :--- | :--- | :--- | :--- |
| **Administrator** | `admin` | `admin@itbis.sec` | `AdminPassword123!` |
| **Security Manager** | `manager_sec` | `manager@itbis.sec` | `ManagerPassword123!` |
| **SOC Engineer** | `soc_eng` | `soc@itbis.sec` | `SocPassword123!` |
| **Security Analyst** | `analyst_l1` | `analyst@itbis.sec` | `AnalystPassword123!` |

---

## 🐳 Docker Setup & Containerization

The project includes Dockerfiles and a `docker-compose.yml` orchestrating **4 services**:
1. `backend` (FastAPI, Port 8000)
2. `frontend` (Next.js, Port 3000)
3. `postgres_db` (PostgreSQL 15, Port 5432)
4. `mongo_db` (MongoDB 7, Port 27017)

### Run Containerized System:
```bash
docker compose config
docker compose up --build
```

---

## 🧪 Testing & Validation

Run the comprehensive end-to-end test suite covering authentication, log ingestion, risk scoring, dashboards, admin authorization, and report exports:

```powershell
cd backend
.\venv\Scripts\python.exe -m unittest discover -s tests
```

---

## ☁️ Cloud Deployment Instructions

### Deployment Readiness
The application is pre-configured for deployment to managed cloud platforms such as **AWS Elastic Beanstalk**, **Azure App Service**, **Render**, or **Google Cloud Run**:
- `DATABASE_URL`, `MONGO_URL`, `SECRET_KEY`, and `NEXT_PUBLIC_API_URL` are completely configurable via environment variables.
- CORS origins dynamically accept production frontend domain configurations.
- Docker containers run unbuffered non-root host bindings (`0.0.0.0:8000` and `0.0.0.0:3000`).

### Cloud Deployment Steps (User Cloud Account Required)
1. **Provision Databases**: Provision managed PostgreSQL (e.g. AWS RDS PostgreSQL) and MongoDB (e.g. MongoDB Atlas or AWS DocumentDB).
2. **Set Cloud Environment Variables**: In your cloud platform console (e.g. AWS Elastic Beanstalk Environment Configuration or Azure App Service App Settings), set:
   - `DATABASE_URL`
   - `MONGO_URL`
   - `SECRET_KEY`
   - `NEXT_PUBLIC_API_URL`
3. **Deploy Container / Service**: Deploy using `docker-compose.yml` or push image repository to AWS ECR / Azure Container Registry.
4. **Model Initialization**: Run `python scripts/run_ml_detection.py` inside the deployed backend container to train the Isolation Forest model on host data.

---

## 🖼️ Dashboard Screenshots

*(Note: Add screenshot previews here for demo presentation)*

- **Analyst Dashboard**: Triage queue, flagged employee roster, and incident dockets.
- **SOC Operations Dashboard**: 7-day anomaly trend line charts and alert severity distributions.
- **Security Manager Dashboard**: Executive threat posture, compliance telemetry, and PDF/Excel report export buttons.
- **Admin Dashboard**: System health indicators, user account breakdown, and platform metrics.

---

## 📄 License

This project is licensed under the [MIT License](LICENSE.md).
