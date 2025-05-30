package app.context;

import config.DatabaseConfig;
import app.ui.BannerDisplayer;
import app.ui.MessageBannerPanel;
import attendance.repository.AttendanceRepository;
import attendance.repository.JdbcAttendanceRepository;
import attendance.service.AttendanceService;
import attendance.service.AttendanceStatsService;
import attendance.service.DefaultAttendanceService;
import attendance.service.JdbcAttendanceStatsService;
import bird.message.BirdMessageDisplayer;
import bird.message.BirdMessageManager;
import bird.message.BirdMessageProvider;
import bird.model.Bird;
import bird.point.DefaultPointService;
import bird.point.PointManager;
import bird.point.PointService;
import bird.repository.*;
import bird.service.BirdService;
import bird.service.DefaultBirdService;
import bird.service.StageEvolutionPolicy;
import coupon.controller.CouponController;
import coupon.repository.CouponRepository;
import coupon.repository.JdbcCouponRepository;
import coupon.service.CouponService;
import coupon.service.DefaultCouponService;
import diary.repository.DiaryRepository;
import diary.repository.JdbcDiaryRepository;
import diary.service.DefaultDiaryService;
import diary.service.DiaryService;
import todo.repository.ToDoRepository;
import todo.repository.JdbcToDoRepository;
import todo.service.ToDoService;
import todo.service.DefaultToDoService;
import user.repository.JdbcUserRepository;
import user.repository.UserRepository;
import user.service.UserService;
import weather.cache.InMemoryWeatherCacheManager;
import weather.cache.WeatherCacheManager;
import weather.fetcher.OpenWeatherFetcher;
import weather.fetcher.WeatherFetcher;
import weather.service.DefaultWeatherService;
import weather.service.WeatherService;

import java.sql.Connection;

public class EarlyBirdContext {

    public final AttendanceService attendanceService;
    public final AttendanceStatsService attendanceStatsService;
    public final PointManager pointManager;
    public final Bird bird;
    public final BirdService birdService;
    public final BirdMessageProvider birdMessageProvider;
    public final WeatherService weatherService;
    public final DiaryService diaryService;
    public final UserService userService;
    public final ToDoService toDoService;
    public final CouponController couponController;
    private final String currentUsername;

    private final MessageBannerPanel bannerPanel;
    private final BirdMessageManager birdMessageManager;

    public final PointService pointService;

    public EarlyBirdContext(String username) {
        // ✅ MessageBannerPanel 먼저 생성
        this.bannerPanel = new MessageBannerPanel();

        // 유저 이름 하드코딩 수정
        this.currentUsername = username;

        // 포인트 관리자 (구)
        pointManager = new PointManager();


        // DB 연결
        Connection conn = DatabaseConfig.getConnection();

        // point db
        PointRepository pointRepo = new JdbcPointRepository(conn);
        pointService = new DefaultPointService(pointRepo);


        // 출석
        AttendanceRepository attendanceRepo = new JdbcAttendanceRepository(conn);
        attendanceService = new DefaultAttendanceService(attendanceRepo, pointService);

        // 출석 통계
        attendanceStatsService = new JdbcAttendanceStatsService(conn);

        // 새 + 메시지
        BirdRepository birdRepository = new JdbcBirdRepository(conn);
        bird = birdRepository.findByUsername(currentUsername);
        birdService = new DefaultBirdService(new StageEvolutionPolicy(), birdRepository, pointService);
        birdMessageProvider = new BirdMessageProvider();
        BirdMessageDisplayer displayer = new BannerDisplayer(bannerPanel);
        birdMessageManager = new BirdMessageManager(bird, birdMessageProvider, displayer);

        // 날씨
        WeatherCacheManager cacheManager = new InMemoryWeatherCacheManager();
        WeatherFetcher fetcher = new OpenWeatherFetcher();
        weatherService = new DefaultWeatherService(cacheManager, fetcher);

        // 일기
        DiaryRepository diaryRepo = new JdbcDiaryRepository(conn);
        diaryService = new DefaultDiaryService(diaryRepo);

        // 유저
        UserRepository userRepo = new JdbcUserRepository(conn);
        userService = new UserService(userRepo);

        // 할 일
        ToDoRepository toDoRepo = new JdbcToDoRepository(conn);
        toDoService = new DefaultToDoService(toDoRepo, pointService);

        // 쿠폰
        CouponRepository couponRepo = new JdbcCouponRepository(conn);
        CouponService couponService = new DefaultCouponService(couponRepo, birdService);
        couponController = new CouponController(couponService, couponRepo);

    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public CouponController getCouponController() {
        return couponController;
    }

    public ToDoService getToDoService() {
        return toDoService;
    }

    public MessageBannerPanel getBannerPanel() {
        return this.bannerPanel;
    }

    public BirdMessageManager getBirdMessageManager() {
        return birdMessageManager;
    }

    public void showAttendanceFrame() {
        new attendance.ui.FrameAttendance(
                attendanceService,
                pointManager,
                bird,
                birdService,
                birdMessageProvider,
                birdMessageManager,
                pointService
        ).setVisible(true);
    }

    public void showAttendanceStatsFrame() {
        new attendance.ui.FrameAttendanceStats(
                attendanceStatsService,
                currentUsername
        ).setVisible(true);
    }

    public void showDiaryFrame() {
        new diary.ui.FrameDiary(
                diaryService,
                birdMessageManager
        ).setVisible(true);
    }
}
