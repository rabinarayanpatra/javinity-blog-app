import React, { useState } from 'react';
import { motion } from 'framer-motion';
import { AiFillHeart, AiOutlineHeart } from 'react-icons/ai';

// Color theme reference
// Primary: #4335A7
// Secondary: #80C4E9
// Background: #FFF6E9
// Accent: #FF7F3E

const ReadBlogPage: React.FC = () => {
    return (
        <div className="bg-[#FFF6E9] text-gray-800 min-h-screen">
            <HeaderSection />
            <MainContent />
        </div>
    );
};

export default ReadBlogPage;

// ------------------ Subcomponents ------------------

const HeaderSection: React.FC = () => {
    return (
        <header className="max-w-4xl mx-auto px-6 py-8">
            <motion.div
                initial={{opacity:0, y:20}}
                animate={{opacity:1, y:0}}
                transition={{duration:0.6}}
                className="space-y-4"
            >
                {/* Author + Date (Left aligned) */}
                <div className="flex items-center space-x-3">
                    <img
                        src="https://via.placeholder.com/40x40?text=A"
                        alt="Author avatar"
                        className="rounded-full"
                    />
                    <div className="text-sm text-gray-600">
                        <p className="font-medium">John Doe</p>
                        <p>Published on June 15, 2024</p>
                    </div>
                </div>

                {/* Post Title (Left-aligned for a modern look) */}
                <h1 className="text-3xl md:text-4xl font-bold text-[#4335A7]">
                    Exploring the Depths of Modern Web Development
                </h1>

                {/* Hero Image */}
                <div className="overflow-hidden rounded-lg mt-6">
                    <img
                        src="https://via.placeholder.com/800x400?text=Featured+Image"
                        alt="Post cover"
                        className="w-full h-auto object-cover transform transition-transform duration-300 hover:scale-105"
                    />
                </div>
            </motion.div>
        </header>
    );
};

const MainContent: React.FC = () => {
    return (
        <main className="max-w-4xl mx-auto px-6 py-8">
            <BlogPostContent />
            <div className="mt-10">
                <InteractionSection />
            </div>
            <AuthorBio />
            <CommentSection />
            <RecommendedPostsSection />
        </main>
    );
};

const BlogPostContent: React.FC = () => {
    return (
        <motion.article
            initial={{opacity:0, y:20}}
            animate={{opacity:1, y:0}}
            transition={{duration:0.6, delay:0.1}}
            className="prose max-w-none text-gray-800"
        >
            <p>
                Welcome to the ever-evolving world of modern web development.
                In recent years, we've seen an explosion of frameworks, libraries,
                and tooling options that have drastically changed how we build for the web.
            </p>
            <p>
                From front-end frameworks like React and Vue to the rise of serverless architectures
                and headless CMS solutions, developers now have a wealth of choices to sculpt
                unique and robust user experiences.
            </p>
            <h2>Understanding the Ecosystem</h2>
            <p>
                The modern ecosystem is diverse. You have state management solutions like Redux,
                MobX, and Recoil, and styling approaches ranging from CSS Modules to Tailwind CSS
                and CSS-in-JS libraries. DevOps practices have entered the front-end space with
                CI/CD pipelines ensuring rapid and reliable deployments.
            </p>
            <p>
                As we push forward, it’s essential not just to pick tools for the sake of it,
                but to understand how each piece fits into the larger puzzle of delivering
                a seamless and performant user experience.
            </p>
            <h3>Performance and User Experience</h3>
            <p>
                Performance is key in the modern web. Users expect lightning-fast load times,
                smooth interactions, and intuitive navigation. Techniques like code splitting,
                SSR (Server-Side Rendering), and CDN caching have become staples for delivering
                snappy applications.
            </p>
            <p>
                Ultimately, modern web development is about making informed choices,
                experimenting with new technologies, and continually learning to
                create cutting-edge experiences for the end user.
            </p>
        </motion.article>
    );
};

const InteractionSection: React.FC = () => {
    const [liked, setLiked] = useState(false);
    const [likeCount, setLikeCount] = useState(42); // example like count

    const handleLike = () => {
        if (liked) {
            setLikeCount(likeCount - 1);
        } else {
            setLikeCount(likeCount + 1);
        }
        setLiked(!liked);
    };

    return (
        <div className="flex items-center space-x-4">
            <motion.button
                initial={{ scale: 1 }}
                animate={{ scale: liked ? 1.1 : 1 }}
                transition={{ type: 'spring', stiffness: 300, damping: 15 }}
                onClick={handleLike}
                className="inline-flex items-center px-4 py-2 rounded-md font-medium transition-colors duration-200 bg-[#80C4E9] text-[#4335A7] hover:bg-[#80C4E9]/80"
            >
                {liked ? (
                    <AiFillHeart className="h-5 w-5 mr-2 text-[#FF7F3E]" />
                ) : (
                    <AiOutlineHeart className="h-5 w-5 mr-2 text-gray-600" />
                )}
                {likeCount}
            </motion.button>
            <a href="#comments" className="text-[#FF7F3E] hover:underline font-medium">
                Leave a Comment
            </a>
        </div>
    );
};

const AuthorBio: React.FC = () => {
    return (
        <motion.section
            initial={{opacity:0, y:20}}
            whileInView={{opacity:1, y:0}}
            transition={{duration:0.6}}
            viewport={{once:true}}
            className="mt-16 bg-white p-6 rounded-lg shadow"
        >
            <div className="flex items-center space-x-4">
                <img
                    src="https://via.placeholder.com/60x60?text=A"
                    alt="Author avatar"
                    className="rounded-full"
                />
                <div>
                    <h3 className="text-xl font-bold text-[#4335A7]">John Doe</h3>
                    <p className="text-gray-600 text-sm">
                        John is a full-stack developer and writer passionate about
                        modern web technologies, user experiences, and making the
                        internet a better place.
                    </p>
                </div>
            </div>
        </motion.section>
    );
};

interface Comment {
    id: number;
    author: string;
    text: string;
    date: string;
}

const CommentSection: React.FC = () => {
    const [comments, setComments] = useState<Comment[]>([
        {id:1, author:'Jane', text:'Great article! Learned a lot.', date:'June 15, 2024'},
        {id:2, author:'Mike', text:'Thanks for sharing!', date:'June 16, 2024'},
    ]);

    const [commentText, setCommentText] = useState('');

    const handleAddComment = (e: React.FormEvent) => {
        e.preventDefault();
        if(!commentText.trim()) return;

        const newComment: Comment = {
            id: Date.now(),
            author: 'You',
            text: commentText.trim(),
            date: 'Just now'
        };
        setComments([...comments, newComment]);
        setCommentText('');
    };

    return (
        <motion.section
            id="comments"
            initial={{opacity:0, y:20}}
            whileInView={{opacity:1, y:0}}
            transition={{duration:0.6}}
            viewport={{once:true}}
            className="mt-16"
        >
            <h2 className="text-2xl font-bold text-[#4335A7] mb-4">Comments</h2>
            <div className="space-y-4 mb-6">
                {comments.map(comment => (
                    <div key={comment.id} className="bg-white p-4 rounded-md shadow">
                        <div className="flex justify-between items-center mb-1">
                            <span className="font-medium text-[#4335A7]">{comment.author}</span>
                            <span className="text-sm text-gray-500">{comment.date}</span>
                        </div>
                        <p className="text-sm text-gray-700">{comment.text}</p>
                    </div>
                ))}
            </div>

            <form onSubmit={handleAddComment} className="flex flex-col sm:flex-row space-y-3 sm:space-y-0 sm:space-x-3">
                <input
                    type="text"
                    value={commentText}
                    onChange={e => setCommentText(e.target.value)}
                    className="flex-1 px-4 py-2 border border-[#80C4E9] rounded-md focus:outline-none focus:border-[#4335A7]"
                    placeholder="Add a comment..."
                />
                <button
                    type="submit"
                    className="px-5 py-2 bg-[#FF7F3E] text-white rounded-md hover:bg-[#ff914f] transition duration-200"
                >
                    Submit
                </button>
            </form>
        </motion.section>
    );
};

const RecommendedPostsSection: React.FC = () => {
    const recommended = [
        {
            title: 'Diving into React Hooks',
            image: 'https://via.placeholder.com/300x200?text=Post+A',
            link: '#'
        },
        {
            title: 'The Future of CSS',
            image: 'https://via.placeholder.com/300x200?text=Post+B',
            link: '#'
        },
        {
            title: 'Backend as a Service: Pros and Cons',
            image: 'https://via.placeholder.com/300x200?text=Post+C',
            link: '#'
        }
    ];

    return (
        <motion.section
            initial={{opacity:0, y:20}}
            whileInView={{opacity:1, y:0}}
            transition={{duration:0.6}}
            viewport={{once:true}}
            className="mt-16"
        >
            <h2 className="text-2xl font-bold text-[#4335A7] mb-6">Recommended Posts</h2>
            <div className="grid md:grid-cols-3 gap-8">
                {recommended.map((post, idx) => (
                    <RecommendedPostCard key={idx} post={post} />
                ))}
            </div>
        </motion.section>
    );
};

interface RecommendedPost {
    title: string;
    image: string;
    link: string;
}

const RecommendedPostCard: React.FC<{post: RecommendedPost}> = ({post}) => {
    return (
        <div className="bg-white rounded-lg shadow hover:shadow-lg transition-shadow duration-300 overflow-hidden">
            <img src={post.image} alt={post.title} className="w-full h-auto object-cover hover:scale-105 transition-transform duration-300" />
            <div className="p-4">
                <h3 className="text-lg font-bold text-[#4335A7] mb-2">{post.title}</h3>
                <a href={post.link} className="text-[#FF7F3E] font-medium hover:underline">
                    Read More →
                </a>
            </div>
        </div>
    );
};